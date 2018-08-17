package org.lotionvirgilabloh.lotionweblog.event;

import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.springframework.context.ApplicationEvent;

import java.util.Map;

public class ExceptionEvent extends ApplicationEvent {

    private static final long serialVersionUID = -4740364740046417008L;

    private Log4jLogEvent log4jLogEvent;

    private Map<String, Object> customProperties;

    public ExceptionEvent(Object source) {
        super(source);
    }

    public ExceptionEvent(Object source, Log4jLogEvent log4jLogEvent) {
        super(source);
        this.log4jLogEvent = log4jLogEvent;
    }

    public ExceptionEvent(Object source, Log4jLogEvent log4jLogEvent, Map<String, Object> customProperties) {
        super(source);
        this.log4jLogEvent = log4jLogEvent;
        this.customProperties = customProperties;
    }

    public Log4jLogEvent getLog4jLogEvent() {
        return log4jLogEvent;
    }

    public void setLog4jLogEvent(Log4jLogEvent log4jLogEvent) {
        this.log4jLogEvent = log4jLogEvent;
    }

    public Map<String, Object> getCustomProperties() {
        return customProperties;
    }

    public void setCustomProperties(Map<String, Object> customProperties) {
        this.customProperties = customProperties;
    }

    @Override
    public String toString() {
        return "ExceptionEvent{" +
                "log4jLogEvent=" + log4jLogEvent +
                ", customProperties=" + customProperties +
                '}';
    }
}