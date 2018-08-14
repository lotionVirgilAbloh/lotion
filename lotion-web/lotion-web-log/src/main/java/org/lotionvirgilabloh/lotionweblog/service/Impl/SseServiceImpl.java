package org.lotionvirgilabloh.lotionweblog.service.Impl;

import org.apache.logging.log4j.core.layout.PatternLayout;
import org.lotionvirgilabloh.lotionweblog.event.ExceptionEvent;
import org.lotionvirgilabloh.lotionweblog.service.SseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

@Service
public class SseServiceImpl implements SseService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Collection<SseEmitter> emitters = Collections.synchronizedCollection(new HashSet<SseEmitter>());

    private final PatternLayout patternLayout = PatternLayout.newBuilder().withPattern("%d{yyyy-MM-dd HH:mm:ss.SSS} -%5p ${PID:-} [%15.15t] %-30.30c{1.} : %m%n").build();

    private void complete(SseEmitter emitter) {
        logger.info("emitter completed");
        emitters.remove(emitter);
    }

    private void timeout(SseEmitter emitter) {
        logger.info("emitter timeout");
        emitters.remove(emitter);
    }

    @Override
    public void register(SseEmitter emitter) {
        emitter.onTimeout(() -> timeout(emitter));
        emitter.onCompletion(() -> complete(emitter));
        emitters.add(emitter);
    }

    @Override
    public void sendSseEventsToUI(ExceptionEvent exceptionEvent) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(patternLayout.toSerializable(exceptionEvent.getLog4jLogEvent()), MediaType.APPLICATION_JSON);
            } catch (Exception e) {
                emitter.complete();
            }
        }
    }

    @EventListener
    public void exceptionEventHandler(ExceptionEvent exceptionEvent) {
        this.sendSseEventsToUI(exceptionEvent);
    }
}
