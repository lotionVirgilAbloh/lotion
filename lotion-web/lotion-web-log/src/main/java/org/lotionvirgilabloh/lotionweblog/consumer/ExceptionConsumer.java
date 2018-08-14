package org.lotionvirgilabloh.lotionweblog.consumer;

import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.jackson.Log4jJsonObjectMapper;
import org.lotionvirgilabloh.lotionweblog.event.ExceptionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.SubscribableChannel;

import java.io.IOException;

@SuppressWarnings("unused")
public class ExceptionConsumer {

    /**
     * InputLotionLogErrorConsumer用于从Kafka获取系统日志ERROR级别的错误信息，并在系统内发布ExceptionEvent
     */
    @EnableBinding(InputLotionLogError.class)
    static class InputLotionLogErrorConsumer {

        @Autowired
        private ApplicationContext applicationContext;

        private final Logger logger = LoggerFactory.getLogger(this.getClass());

        @StreamListener(InputLotionLogError.INPUT)
        public void receive(String data) {
            logger.info("InputLotionLogErrorConsumer data received..." + data);
            Log4jLogEvent log4jLogEvent = null;
            try {
                log4jLogEvent = (new Log4jJsonObjectMapper()).readValue(data, Log4jLogEvent.class);
            } catch (IOException e) {
                logger.error("Log4jLogEvent转换失败", e);
            }
            applicationContext.publishEvent(new ExceptionEvent(this, log4jLogEvent));
        }
    }

    /**
     * 定义InputLotionLogError接口，会从配置文件中自动配置Kafka接口
     */
    interface InputLotionLogError {

        String INPUT = "inputLotionLogError";

        @Input(INPUT)
        SubscribableChannel inputLotionLogError();

    }
}
