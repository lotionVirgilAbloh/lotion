package org.lotionvirgilabloh.lotionwebcontrol.service;

import org.lotionvirgilabloh.lotionwebcontrol.entity.JSchReturn;

public interface SshService {
    JSchReturn exec(String destination, String username, String password, String cmd);
    JSchReturn shell(String destination, String username, String password, String[] cmd);
    JSchReturn transferFile(String destination, String username, String password, String path, String filename, String[] contents);
    JSchReturn checkDirectory(String destination, String username, String password, String path);
    JSchReturn makeDirectory(String destination, String username, String password, String path);
    JSchReturn checkFile(String destination, String username, String password, String path, String filename);
    JSchReturn makeFile(String destination, String username, String password, String path, String filename, String content);
    JSchReturn makeFile(String destination, String username, String password, String path, String filename, String[] contents);
    JSchReturn deleteFile(String destination, String username, String password, String path, String filename);
    JSchReturn deleteDirectory(String destination, String username, String password, String path);
    JSchReturn execSh(String destination, String username, String password, String path, String shname);
}
