package org.lotionvirgilabloh.lotionweblog.service;

import org.lotionvirgilabloh.lotionweblog.event.ExceptionEvent;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseService {
    void register(SseEmitter emitter);

    void sendSseEventsToUI(ExceptionEvent exceptionEvent);
}
