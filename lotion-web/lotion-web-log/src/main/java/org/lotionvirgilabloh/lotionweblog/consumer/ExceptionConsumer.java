package org.lotionvirgilabloh.lotionweblog.consumer;

import com.fasterxml.jackson.databind.DeserializationFeature;
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
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class ExceptionConsumer {

    /**
     * InputLotionLogErrorConsumer用于从Kafka获取系统日志ERROR级别的错误信息，并在系统内发布ExceptionEvent
     */
    @EnableBinding(InputLotionLogError.class)
    static class InputLotionLogErrorConsumer {

        @Autowired
        private ApplicationContext applicationContext;

        private final Log4jJsonObjectMapper log4jJsonObjectMapper = new Log4jJsonObjectMapper();

        private final Logger logger = LoggerFactory.getLogger(this.getClass());

        @StreamListener(InputLotionLogError.INPUT)
        public void receive(String data) {
            logger.info("InputLotionLogErrorConsumer data received..." + data);
            Map map;
            Log4jLogEvent log4jLogEvent = null;
            Map<String, String> customProperties = new HashMap<>();
            try {
                //保证转换时不会发生unknown properties的错误
                if (log4jJsonObjectMapper.getDeserializationConfig().isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES))
                    log4jJsonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                //先转换为map对象
                map = log4jJsonObjectMapper.readValue(data, Map.class);
                //从map对象转换到log4jLogEvent
                log4jLogEvent = log4jJsonObjectMapper.convertValue(map, Log4jLogEvent.class);
                //自定义custom properties的转换
                if (map.containsKey("project"))
                    customProperties.put("project", (String) map.get("project"));
                if (map.containsKey("date"))
                    customProperties.put("date", (String) map.get("date"));
            } catch (IOException e) {
                logger.error("Log4j2转换失败", e);
            }
            ExceptionEvent exceptionEvent = new ExceptionEvent(this, log4jLogEvent, customProperties);
            applicationContext.publishEvent(exceptionEvent);
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
