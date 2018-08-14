package org.lotionvirgilabloh.lotionweblog.service;

import org.lotionvirgilabloh.lotionweblog.event.ExceptionEvent;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseService {

    /**
     * 服务内注册SseEmitter
     *
     * @param emitter
     */
    void register(SseEmitter emitter);

    /**
     * 将ExceptionEvent事件格式化后发送至前端
     *
     * @param exceptionEvent
     */
    void sendSseEventsToUI(ExceptionEvent exceptionEvent);
}
