package org.lotionvirgilabloh.lotionwebcontrol.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.lotionvirgilabloh.lotionbase.dto.OfflineJob;
import org.lotionvirgilabloh.lotionbase.dto.RealtimeJob;
import org.lotionvirgilabloh.lotionwebcontrol.configuration.LotionJsCHProperties;
import org.lotionvirgilabloh.lotionwebcontrol.entity.JSchReturn;
import org.lotionvirgilabloh.lotionbase.dto.PagerModel;
import org.lotionvirgilabloh.lotionwebcontrol.service.JobDaoService;
import org.lotionvirgilabloh.lotionwebcontrol.service.SshService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/jobdao/")
public class JobDaoController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JobDaoService jobDaoService;

    @Autowired
    private SshService sshService;

    @Autowired
    private LotionJsCHProperties lotionJsCHProperties;

    @RequestMapping("{type}/getById")
    public <T> T getById(@PathVariable("type") String type, Long id) {
        logger.info("JobDaoController获取请求:/jobdao/" + type + "/getById?id=" + id.toString());
        List<T> t = jobDaoService.getById(type, id);
        if (t == null || t.size() == 0)
            return null;
        return t.get(0);
    }

    @RequestMapping("{type}/getByJobname")
    public <T> T getByJobname(@PathVariable("type") String type, String jobname) {
        logger.info("JobDaoController获取请求:/jobdao/" + type + "/getByJobname?id=" + jobname);
        List<T> t = jobDaoService.getByJobname(type, jobname);
        if (t == null || t.size() == 0)
            return null;
        return t.get(0);
    }

    @RequestMapping("{type}/getAll")
    public <T> PagerModel<T> getAll(@PathVariable("type") String type) {
        logger.info("JobDaoController获取请求:/jobdao/" + type + "/getAll");
        List<T> jobs = jobDaoService.getAll(type);
        if (jobs == null) {
            jobs = new ArrayList<>();
        }
        PagerModel<T> pagerModel = new PagerModel<>();
        pagerModel.setData(jobs);
        pagerModel.setRecordsFiltered(jobs.size());
        pagerModel.setRecordsTotal(jobs.size());
        return pagerModel;
    }

    @RequestMapping("{type}/deleteByJobname/{jobname}")
    public <T> boolean deleteByJobname(@PathVariable("type") String type, @PathVariable("jobname") String jobname) {
        logger.info("JobDaoController获取请求:/jobdao/" + type + "/deleteByJobname/" + jobname);
        List<T> t = jobDaoService.getByJobname(type, jobname);
        if (t == null || t.size() == 0) {
            logger.error("MySQL删除失败，查询条目失败");
            return false;
        }

        boolean flag = jobDaoService.deleteByJobname(type, jobname);
        if (!flag) {
            logger.error("MySQL删除失败");
            return false;
        }

        // 执行JsCH删除
        if (lotionJsCHProperties.getEnabled()) {
            logger.info("执行JsCH删除");
            JSchReturn j;
            if (lotionJsCHProperties.getBuiltin()) {
                j = sshService.deleteDirectory(lotionJsCHProperties.getUrl(), lotionJsCHProperties.getUsername(), lotionJsCHProperties.getPassword(), lotionJsCHProperties.getStoreShPath() + jobname + '/');
            } else {
                j = sshService.deleteDirectory(lotionJsCHProperties.getUrl(), ((String) ((LinkedHashMap) t.get(0)).get("username")), ((String) ((LinkedHashMap) t.get(0)).get("password")), lotionJsCHProperties.getStoreShPath() + jobname + '/');
            }
            logger.info(j.toString());
            flag = j.isSuccess();
            if (!flag) {
                logger.error("JsCH删除失败");
                return false;
            }
        }
        return true;
    }

    @RequestMapping("{type}/action")
    public <T> boolean action(@PathVariable("type") String type, String jobname) {
        logger.info("JobDaoController获取请求:/jobdao/" + type + "/action?id=" + jobname);
        boolean flag;
        // 0选择startSh，1选择stopSh
        int whichSh = 0;
        List<T> t = jobDaoService.getByJobname(type, jobname);
        if (t == null || t.size() == 0)
            return false;
        switch (type) {
            case "rt":
                RealtimeJob realtimeJob = (new ObjectMapper()).convertValue((LinkedHashMap) t.get(0), RealtimeJob.class);
                if (realtimeJob.getStatus() == 0) {
                    realtimeJob.setStatus(1);
                } else {
                    realtimeJob.setStatus(0);
                    whichSh = 1;
                }
                flag = jobDaoService.updateByJobname(type, realtimeJob);
                if(!flag) {
                    logger.error("MySQL动作失败");
                    return false;
                }

                // 执行JsCH动作
                if (lotionJsCHProperties.getEnabled()) {
                    logger.info("执行JsCH动作");
                    JSchReturn j;
                    if (whichSh == 1) {
                        if (lotionJsCHProperties.getBuiltin()) {
                            j = sshService.execSh(lotionJsCHProperties.getUrl(), lotionJsCHProperties.getUsername(), lotionJsCHProperties.getPassword(), lotionJsCHProperties.getStoreShPath() + realtimeJob.getJobname() + '/', realtimeJob.getJobname() + "_startSh.sh");
                        } else {
                            j = sshService.execSh(realtimeJob.getDestination(), realtimeJob.getUsername(), realtimeJob.getPassword(), lotionJsCHProperties.getStoreShPath() + realtimeJob.getJobname() + '/', realtimeJob.getJobname() + "_startSh.sh");
                        }
                    } else {
                        if (lotionJsCHProperties.getBuiltin()) {
                            j = sshService.execSh(lotionJsCHProperties.getUrl(), lotionJsCHProperties.getUsername(), lotionJsCHProperties.getPassword(), lotionJsCHProperties.getStoreShPath() + realtimeJob.getJobname() + '/', realtimeJob.getJobname() + "_stopSh.sh");
                        } else {
                            j = sshService.execSh(realtimeJob.getDestination(), realtimeJob.getUsername(), realtimeJob.getPassword(), lotionJsCHProperties.getStoreShPath() + realtimeJob.getJobname() + '/', realtimeJob.getJobname() + "_stopSh.sh");
                        }
                    }
                    logger.info(j.toString());
                    flag = j.isSuccess();
                    if (!flag) {
                        logger.error("JsCH动作失败");
                        return false;
                    }
                }
                break;
            case "of":
                OfflineJob offlineJob = (new ObjectMapper()).convertValue((LinkedHashMap) t.get(0), OfflineJob.class);
                offlineJob.setLastrun(new Date());
                flag = jobDaoService.updateByJobname(type, offlineJob);
                if(!flag) {
                    logger.error("MySQL动作失败");
                    return false;
                }

                // 执行JsCH动作
                if (lotionJsCHProperties.getEnabled()) {
                    logger.info("执行JsCH动作");
                    JSchReturn j;
                    if (lotionJsCHProperties.getBuiltin()) {
                        j = sshService.execSh(lotionJsCHProperties.getUrl(), lotionJsCHProperties.getUsername(), lotionJsCHProperties.getPassword(), lotionJsCHProperties.getStoreShPath() + offlineJob.getJobname() + '/', offlineJob.getJobname() + "_startSh.sh");
                    } else {
                        j = sshService.execSh(offlineJob.getDestination(), offlineJob.getUsername(), offlineJob.getPassword(), lotionJsCHProperties.getStoreShPath() + offlineJob.getJobname() + '/', offlineJob.getJobname() + "_startSh.sh");
                    }
                    logger.info(j.toString());
                    flag = j.isSuccess();
                    if (!flag) {
                        logger.error("JsCH动作失败");
                        return false;
                    }
                }
                break;
            default:
                return false;
        }
        return true;
    }

    @RequestMapping("{type}/save")
    public boolean save(@PathVariable("type") String type, HttpServletRequest request) {
        logger.info("JobDaoController获取请求:/jobdao/" + type + "/save");
        boolean flag;
        switch (type) {
            case "rt":
                //由于表单提交过来的对象不明确，需要利用HttpServletRequest.getParameter的方式获取属性以重建实体
                //另外可以通过利用Json转化为Map的形式进行反序列化，在这里并没有实现
                RealtimeJob realtimeJob = new RealtimeJob();
                realtimeJob.setJobname(request.getParameter("jobname"));
                realtimeJob.setDestination(request.getParameter("destination"));
                realtimeJob.setType(Integer.parseInt(request.getParameter("type")));
                //默认新创建的对象状态为"停止"即status=0
                realtimeJob.setStatus(0);
                realtimeJob.setUsername(request.getParameter("username"));
                realtimeJob.setPassword(request.getParameter("password"));
                realtimeJob.setStartsh(request.getParameter("startsh"));
                realtimeJob.setStopsh(request.getParameter("stopsh"));
                if (jobDaoService.getByJobname(type, (realtimeJob).getJobname()) == null) {
                    flag = jobDaoService.save(type, realtimeJob);
                } else {
                    flag = jobDaoService.updateByJobname(type, realtimeJob);
                }
                break;
            case "of":
                OfflineJob offlineJob = new OfflineJob();
                offlineJob.setJobname(request.getParameter("jobname"));
                offlineJob.setType(Integer.parseInt(request.getParameter("type")));
                //无上次提交时间
                offlineJob.setLastrun(null);
                offlineJob.setDestination(request.getParameter("destination"));
                offlineJob.setUsername(request.getParameter("username"));
                offlineJob.setPassword(request.getParameter("password"));
                offlineJob.setStartsh(request.getParameter("startsh"));
                if (jobDaoService.getByJobname(type, (offlineJob).getJobname()) == null) {
                    flag = jobDaoService.save(type, offlineJob);
                } else {
                    flag = jobDaoService.updateByJobname(type, offlineJob);
                }
                break;
            default:
                return false;
        }
        if (!flag) {
            logger.error("MySQL保存失败");
        }

        // 执行JsCH保存
        if (lotionJsCHProperties.getEnabled()) {
            logger.info("执行JsCH保存");
            String[] startShContents = request.getParameter("startsh").split("\n");
            JSchReturn j1;
            if (lotionJsCHProperties.getBuiltin()) {
                j1 = sshService.transferFile(lotionJsCHProperties.getUrl(), lotionJsCHProperties.getUsername(), lotionJsCHProperties.getPassword(), lotionJsCHProperties.getStoreShPath() + request.getParameter("jobname") + '/', request.getParameter("jobname") + "_startSh.sh", startShContents);
            } else {
                j1 = sshService.transferFile(lotionJsCHProperties.getUrl(), request.getParameter("username"), request.getParameter("password"), lotionJsCHProperties.getStoreShPath() + request.getParameter("jobname") + '/', request.getParameter("jobname") + "_startSh.sh", startShContents);
            }
            logger.info(j1.toString());
            flag = j1.isSuccess();
            if (!flag) {
                logger.error("JsCH保存startSh失败");
                return false;
            }
            switch (type) {
                case "rt":
                    String[] stopShContents = request.getParameter("stopsh").split("\n");
                    JSchReturn j2;
                    if (lotionJsCHProperties.getBuiltin()) {
                        j2 = sshService.transferFile(lotionJsCHProperties.getUrl(), lotionJsCHProperties.getUsername(), lotionJsCHProperties.getPassword(), lotionJsCHProperties.getStoreShPath() + request.getParameter("jobname") + '/', request.getParameter("jobname") + "_stopSh.sh", stopShContents);
                    } else {
                        j2 = sshService.transferFile(lotionJsCHProperties.getUrl(), request.getParameter("username"), request.getParameter("password"), lotionJsCHProperties.getStoreShPath() + request.getParameter("jobname") + '/', request.getParameter("jobname") + "_stopSh.sh", stopShContents);
                    }
                    logger.info(j2.toString());
                    flag = j2.isSuccess();
                    if (!flag) {
                        logger.error("JsCH保存stopSh失败");
                        return false;
                    }
                    break;
                case "of":
                    break;
                default:
                    return false;
            }
        }
        return true;
    }
}