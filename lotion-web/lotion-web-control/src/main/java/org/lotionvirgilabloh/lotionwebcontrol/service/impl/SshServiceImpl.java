package org.lotionvirgilabloh.lotionwebcontrol.service.impl;

import com.jcraft.jsch.*;
import org.lotionvirgilabloh.lotionwebcontrol.entity.JSchReturn;
import org.lotionvirgilabloh.lotionwebcontrol.service.SshService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
@SuppressWarnings(value = "all")
public class SshServiceImpl implements SshService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * {@inheritDoc}
     */
    @Override
    public JSchReturn exec(String destination, String username, String password, String cmd) {
        JSchReturn jSchReturn = new JSchReturn();
        BufferedReader reader = null;
        ChannelExec channel = null;
        Session session = null;
        try {
            JSch jSch = new JSch();
            session = jSch.getSession(username, destination);
            session.setPassword(password);
            Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();

            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(cmd);
            channel.setInputStream(null);
            channel.setErrStream(System.err);
            logger.info("JSch send:" + cmd);
            channel.connect();


            InputStream in = channel.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
            List<String> msgs = new ArrayList<>();
            String msg;
            while ((msg = reader.readLine()) != null) {
                msgs.add(msg);
                logger.info("JSch return:" + msg);
            }
            jSchReturn.setSuccess(true);
            jSchReturn.setReturns(msgs);
            return jSchReturn;

        } catch (JSchException e) {
            logger.error("JSchException", e);
            return jSchReturn;

        } catch (IOException e) {
            logger.error("IOException", e);
            return jSchReturn;

        } finally {
            try {
                reader.close();
                channel.disconnect();
                session.disconnect();
            } catch (IOException e) {
                logger.error("IOException", e);
            } catch (NullPointerException e) {
                logger.error("NullPointerException", e);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JSchReturn shell(String destination, String username, String password, String[] cmds) {
        JSchReturn jSchReturn = new JSchReturn();
        BufferedReader reader = null;
        ChannelShell channel = null;
        Session session = null;

        try {
            JSch jSch = new JSch();
            session = jSch.getSession(username, destination);
            session.setPassword(password);
            Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();

            channel = (ChannelShell) session.openChannel("shell");
            channel.connect();

            InputStream inputStream = channel.getInputStream();//从远程端到达的所有数据都能从这个流中读取到
            OutputStream outputStream = channel.getOutputStream();//写入该流的所有数据都将发送到远程端。
            //使用PrintWriter流的目的就是为了使用println这个方法
            //好处就是不需要每次手动给字符串加\n
            PrintWriter printWriter = new PrintWriter(outputStream);

            for(int i = 0; i < cmds.length; i++) {
                logger.info("JSch send:" + cmds[i]);
                printWriter.println(cmds[i]);
            }
            printWriter.println("exit");
            printWriter.flush();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            List<String> msgs = new ArrayList<>();
            String msg;
            while((msg = reader.readLine())!=null){
                msgs.add(msg);
                logger.info("JSch return:" + msg);
            }
            jSchReturn.setSuccess(true);
            jSchReturn.setReturns(msgs);
            return jSchReturn;

        } catch (JSchException e) {
            logger.error("JSchException", e);
            return jSchReturn;

        } catch (IOException e) {
            logger.error("IOException", e);
            return jSchReturn;

        } finally {
            try {
                reader.close();
                channel.disconnect();
                session.disconnect();
            } catch (IOException e) {
                logger.error("IOException", e);
            } catch (NullPointerException e) {
                logger.error("NullPointerException", e);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JSchReturn transferFile(String destination, String username, String password, String path, String filename, String[] contents) {
        // checkDirectory，不存在则创建
        JSchReturn jSchReturn = new JSchReturn();
        List<String> msgs = new ArrayList<>();
        JSchReturn j1 = checkDirectory(destination, username, password, path);
        msgs.addAll(j1.getReturns());
        if (!j1.isSuccess()) {
            jSchReturn.setReturns(msgs);
            jSchReturn.setReturnType(2);
            return jSchReturn;
        }
        if (j1.getReturnType() != 1) {
            if (j1.getReturnType() == 2) {
                JSchReturn j2 = makeDirectory(destination, username, password, path);
                msgs.addAll(j2.getReturns());
                if (!j2.isSuccess()) {
                    jSchReturn.setReturns(msgs);
                    jSchReturn.setReturnType(3);
                    return jSchReturn;
                }
            } else {
                jSchReturn.setReturns(msgs);
                jSchReturn.setReturnType(4);
                return jSchReturn;
            }
        }

        // makeFile，ssh创建文件
        JSchReturn j3 = makeFile(destination, username, password, path, filename, contents);
        msgs.addAll(j3.getReturns());
        if(!j3.isSuccess()) {
            jSchReturn.setReturns(msgs);
            jSchReturn.setReturnType(5);
            return jSchReturn;
        }

        jSchReturn.setSuccess(true);
        jSchReturn.setReturns(msgs);
        jSchReturn.setReturnType(1);

        return jSchReturn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JSchReturn checkDirectory(String destination, String username, String password, String path) {
        String cmdCheckDirectory = "if [ -d \"" + path + "\" ]; then--echo \"dirtrue\"--else--echo \"dirfalse\"--fi";
        String[] cmdCheckDirectoryList = cmdCheckDirectory.split("--");

        JSchReturn jSchReturn = shell(destination, username, password, cmdCheckDirectoryList);

        for(String msg : jSchReturn.getReturns()) {
            if (msg.equals("dirtrue")) {
                jSchReturn.setSuccess(true);
                jSchReturn.setReturnType(1);
                return jSchReturn;
            }
            if (msg.equals("dirfalse")) {
                jSchReturn.setSuccess(true);
                jSchReturn.setReturnType(2);
                return jSchReturn;
            }
        }

        return jSchReturn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JSchReturn makeDirectory(String destination, String username, String password, String path) {
        String cmdMakeDirectory = "mkdir -p " + path;
        return exec(destination, username, password, cmdMakeDirectory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JSchReturn checkFile(String destination, String username, String password, String path, String filename) {
        String cmdCheckDirectory = "if [ -f \"" + path + filename + "\" ]; then--echo \"filetrue\"--else--echo \"filefalse\"--fi";
        String[] cmdCheckDirectoryList = cmdCheckDirectory.split("--");

        JSchReturn jSchReturn = shell(destination, username, password, cmdCheckDirectoryList);

        for(String msg : jSchReturn.getReturns()) {
            if (msg.equals("filetrue")) {
                jSchReturn.setSuccess(true);
                jSchReturn.setReturnType(1);
                return jSchReturn;
            }
            if (msg.equals("fliefalse")) {
                jSchReturn.setSuccess(true);
                jSchReturn.setReturnType(2);
                return jSchReturn;
            }
        }

        return jSchReturn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JSchReturn makeFile(String destination, String username, String password, String path, String filename, String content) {
        // 防止与echo的单引号混淆，需要调整单引号的输出
        String cmdMakeFile = "echo \'" + content.replaceAll("\'", "\'\"\'\"\'") + "\' > " + path + filename;
        return exec(destination, username, password, cmdMakeFile);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JSchReturn makeFile(String destination, String username, String password, String path, String filename, String[] contents) {
        if (contents.length < 1) {
            return new JSchReturn();
        }
        String[] cmdMakeFiles = new String[contents.length];
        // 防止与echo的单引号混淆，需要调整单引号的输出
        // 第一行重新创建文件
        cmdMakeFiles[0] = "echo \'" + contents[0].replaceAll("\'", "\'\"\'\"\'") + "\' > " + path + filename;
        for(int i = 1; i < contents.length; i++) {
            cmdMakeFiles[i] = "echo \'" + contents[i].replaceAll("\'", "\'\"\'\"\'") + "\' >> " + path + filename;
        }

        return shell(destination, username, password, cmdMakeFiles);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JSchReturn deleteFile(String destination, String username, String password, String path, String filename) {
        String cmdDelteFile = "rm -f " + path + filename;
        return exec(destination, username, password, cmdDelteFile);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JSchReturn deleteDirectory(String destination, String username, String password, String path) {
        String cmdDelteDirectory = "rm -rf " + path;
        return exec(destination, username, password, cmdDelteDirectory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JSchReturn execSh(String destination, String username, String password, String path, String shname) {
        String cmdExecSh = path + shname;
        return exec(destination, username, password, cmdExecSh);
    }
}
