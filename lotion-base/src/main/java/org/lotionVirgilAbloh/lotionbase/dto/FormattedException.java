package org.lotionVirgilAbloh.lotionbase.dto;

import java.io.Serializable;
import java.util.Map;
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
     * 其余参数
     */
    private Map<String, Object> additionalProperties;

    public FormattedException() {
    }

    public FormattedException(long timeMillis, String message, String project, Map<String, Object> additionalProperties) {
        this.timeMillis = timeMillis;
        this.message = message;
        this.project = project;
        this.additionalProperties = additionalProperties;
    }

    public FormattedException(String exceptionID, long timeMillis, String message, String project, Map<String, Object> additionalProperties) {
        this.exceptionID = exceptionID;
        this.timeMillis = timeMillis;
        this.message = message;
        this.project = project;
        this.additionalProperties = additionalProperties;
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

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormattedException that = (FormattedException) o;
        return getTimeMillis() == that.getTimeMillis() &&
                Objects.equals(getExceptionID(), that.getExceptionID()) &&
                Objects.equals(getMessage(), that.getMessage()) &&
                Objects.equals(getProject(), that.getProject()) &&
                Objects.equals(getAdditionalProperties(), that.getAdditionalProperties());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getExceptionID(), getTimeMillis(), getProject());
    }
}
