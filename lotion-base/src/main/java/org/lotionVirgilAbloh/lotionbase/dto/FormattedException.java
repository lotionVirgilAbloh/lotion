package org.lotionVirgilAbloh.lotionbase.dto;

import java.io.Serializable;
import java.util.Objects;

public class FormattedException implements Serializable {

    private static final long serialVersionUID = 362857624711404826L;

    /**
     * 标识该Exception的ID
     */
    private String exceptionID;

    /**
     * 发生Exception的时间
     */
    private long timeMillis;

    /**
     * Exception的消息
     */
    private String message;

    /**
     * 发生Exception的项目
     */
    private String project;

    /**
     * 发生Exception的loggerName
     */
    private String loggerName;

    /**
     * 发生Exception的线程
     */
    private String thread;

    /**
     * 发生Exception的日期
     */
    private String date;

    public FormattedException() {
    }

    public FormattedException(String exceptionID, long timeMillis, String message, String project, String loggerName, String thread, String date) {
        this.exceptionID = exceptionID;
        this.timeMillis = timeMillis;
        this.message = message;
        this.project = project;
        this.loggerName = loggerName;
        this.thread = thread;
        this.date = date;
    }

    public String getExceptionID() {
        return exceptionID;
    }

    public void setExceptionID(String exceptionID) {
        this.exceptionID = exceptionID;
    }

    public long getTimeMillis() {
        return timeMillis;
    }

    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "FormattedException{" +
                "exceptionID='" + exceptionID + '\'' +
                ", timeMillis=" + timeMillis +
                ", message='" + message + '\'' +
                ", project='" + project + '\'' +
                ", loggerName='" + loggerName + '\'' +
                ", thread='" + thread + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTimeMillis(), getMessage(), getProject(), getLoggerName(), getThread(), getDate());
    }
}
