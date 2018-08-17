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

    /**
     * SseEmitter池
     */
    private final Collection<SseEmitter> emitters = Collections.synchronizedCollection(new HashSet<>());

    /**
     * 格式化输出Log4jLogEvent
     */
    private final PatternLayout patternLayout = PatternLayout.newBuilder().withPattern("%d{yyyy-MM-dd HH:mm:ss.SSS} -%highlight{%5p} ${PID:-} [%15.15t] %-30.30c{1.} : %m%n").build();

    private void complete(SseEmitter emitter) {
        logger.info("emitter" + emitter.hashCode() + " completed");
        emitters.remove(emitter);
    }

    private void timeout(SseEmitter emitter) {
        logger.info("emitter" + emitter.hashCode() + " timeout");
        emitters.remove(emitter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void register(SseEmitter emitter) {
        emitter.onTimeout(() -> timeout(emitter));
        emitter.onCompletion(() -> complete(emitter));
        emitters.add(emitter);
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * 用于接收系统ExceptionEvent事件，并调用sendSseEventsToUI将事件发送至前端实时显示
     *
     * @param exceptionEvent
     */
    @EventListener
    public void exceptionEventHandler(ExceptionEvent exceptionEvent) {
        this.sendSseEventsToUI(exceptionEvent);
    }
}
