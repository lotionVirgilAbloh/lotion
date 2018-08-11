package org.lotionvirgilabloh.lotionwebcontrol.service;

import org.lotionvirgilabloh.lotionwebcontrol.entity.JSchReturn;

public interface SshService {
    /**
     * @param destination
     * @param username
     * @param password
     * @param cmd
     * @return
     */
    JSchReturn exec(String destination, String username, String password, String cmd);

    /**
     * @param destination
     * @param username
     * @param password
     * @param cmd
     * @return
     */
    JSchReturn shell(String destination, String username, String password, String[] cmd);

    /**
     * @param destination
     * @param username
     * @param password
     * @param path
     * @param filename
     * @param contents
     * @return
     */
    JSchReturn transferFile(String destination, String username, String password, String path, String filename, String[] contents);

    /**
     * @param destination
     * @param username
     * @param password
     * @param path
     * @return
     */
    JSchReturn checkDirectory(String destination, String username, String password, String path);

    /**
     * @param destination
     * @param username
     * @param password
     * @param path
     * @return
     */
    JSchReturn makeDirectory(String destination, String username, String password, String path);

    /**
     * @param destination
     * @param username
     * @param password
     * @param path
     * @param filename
     * @return
     */
    JSchReturn checkFile(String destination, String username, String password, String path, String filename);

    /**
     * @param destination
     * @param username
     * @param password
     * @param path
     * @param filename
     * @param content
     * @return
     */
    JSchReturn makeFile(String destination, String username, String password, String path, String filename, String content);

    /**
     * @param destination
     * @param username
     * @param password
     * @param path
     * @param filename
     * @param contents
     * @return
     */
    JSchReturn makeFile(String destination, String username, String password, String path, String filename, String[] contents);

    /**
     * @param destination
     * @param username
     * @param password
     * @param path
     * @param filename
     * @return
     */
    JSchReturn deleteFile(String destination, String username, String password, String path, String filename);

    /**
     * @param destination
     * @param username
     * @param password
     * @param path
     * @return
     */
    JSchReturn deleteDirectory(String destination, String username, String password, String path);

    /**
     * @param destination
     * @param username
     * @param password
     * @param path
     * @param shname
     * @return
     */
    JSchReturn execSh(String destination, String username, String password, String path, String shname);
}
