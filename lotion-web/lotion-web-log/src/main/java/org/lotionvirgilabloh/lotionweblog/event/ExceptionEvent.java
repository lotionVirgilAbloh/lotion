package org.lotionvirgilabloh.lotionweblog.event;

import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.springframework.context.ApplicationEvent;

public class ExceptionEvent extends ApplicationEvent {

    private Log4jLogEvent log4jLogEvent;

    public ExceptionEvent(Object source) {
        super(source);
    }

    public ExceptionEvent(Object source, Log4jLogEvent log4jLogEvent) {
        super(source);
        this.log4jLogEvent = log4jLogEvent;
    }

    public Log4jLogEvent getLog4jLogEvent() {
        return log4jLogEvent;
    }

    public void setLog4jLogEvent(Log4jLogEvent log4jLogEvent) {
        this.log4jLogEvent = log4jLogEvent;
    }
}