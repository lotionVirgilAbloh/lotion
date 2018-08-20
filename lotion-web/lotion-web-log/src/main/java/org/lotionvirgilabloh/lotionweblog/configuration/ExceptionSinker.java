package org.lotionvirgilabloh.lotionweblog.configuration;

import org.lotionvirgilabloh.lotionbase.dto.FormattedException;
import org.lotionvirgilabloh.lotionweblog.event.ExceptionEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class ExceptionSinker {

    @EventListener
    public void exceptionEventHandler(ExceptionEvent exceptionEvent) {
        Map<String, Object> addtionalProperties = new LinkedHashMap<>(exceptionEvent.getCustomProperties());
        //project不属于addtionalProperties
        addtionalProperties.remove("project");
        addtionalProperties.put("loggerName", exceptionEvent.getLog4jLogEvent().getLoggerName());
        addtionalProperties.put("threadID", exceptionEvent.getLog4jLogEvent().getThreadId());
        addtionalProperties.put("threadName", exceptionEvent.getLog4jLogEvent().getThreadName());

        //构造FormattedException
        String project;
        if (exceptionEvent.getCustomProperties().containsKey("project"))
            project = exceptionEvent.getCustomProperties().get("project").toString();
        else
            project = null;
        FormattedException formattedException = new FormattedException(exceptionEvent.getLog4jLogEvent().getTimeMillis(), exceptionEvent.getLog4jLogEvent().getMessage().toString(), project, addtionalProperties);
        //TODO:接下来需要落地到mongodb
    }
}
