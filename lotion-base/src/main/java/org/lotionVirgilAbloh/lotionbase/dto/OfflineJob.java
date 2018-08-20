package org.lotionvirgilabloh.lotionbase.dto;

import java.io.Serializable;
import java.util.Date;

public class OfflineJob implements Serializable {


    private static final long serialVersionUID = 2998848203740496505L;

    public OfflineJob() {

    }

    /**
     * 任务名
     */
    private String jobname;
    /**
     * 类型
     */
    private int type;
    /**
     * 记录上次运行时间
     */
    private Date lastrun;
    /**
     * 目的机
     */
    private String destination;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 启动脚本
     */
    private String startsh;

    public OfflineJob(String jobname, int type, Date lastrun, String destination, String username, String password, String startsh) {
        this.jobname = jobname;
        this.type = type;
        this.lastrun = lastrun;
        this.destination = destination;
        this.username = username;
        this.password = password;
        this.startsh = startsh;
    }

    public OfflineJob(String jobname, int type, Date lastrun, String destination, String username, String password) {
        this.jobname = jobname;
        this.type = type;
        this.lastrun = lastrun;
        this.destination = destination;
        this.username = username;
        this.password = password;
    }

    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getLastrun() {
        return lastrun;
    }

    public void setLastrun(Date lastrun) {
        this.lastrun = lastrun;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStartsh() {
        return startsh;
    }

    public void setStartsh(String startsh) {
        this.startsh = startsh;
    }

    @Override
    public String toString() {
        return "OfflineJob{" +
                "jobname='" + jobname + '\'' +
                ", type=" + type +
                ", lastrun=" + lastrun +
                ", destination='" + destination + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", startsh='" + startsh + '\'' +
                '}';
    }
}
