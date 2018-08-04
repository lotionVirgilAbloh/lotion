package org.lotionvirgilabloh.lotiondaomysql.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "control_offlinejob")
public class DBOfflineJob {

    /**
     * 主键，主键不参与业务逻辑
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    /**
     * 任务名
     */
    @Column(name="jobname")
    private String jobname;
    /**
     * 类型
     */
    @Column(name="type")
    private int type;
    /**
     * 记录上次运行时间
     */
    @Column(name="lastrun")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date lastrun;
    /**
     * 目的机
     */
    @Column(name="destination")
    private String destination;
    /**
     * 用户名
     */
    @Column(name="username")
    private String username;
    /**
     * 密码
     */
    @Column(name="password")
    private String password;
    /**
     * 启动脚本
     */
    @Column(name="startsh")
    private String startsh;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
