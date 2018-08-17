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
import java.util.LinkedHashMap;
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
            Map<?, ?> map;
            Log4jLogEvent log4jLogEvent = null;
            Map<String, Object> customProperties = new LinkedHashMap<>();
            try {
                //保证转换时不会发生unknown properties的错误
                if (log4jJsonObjectMapper.getDeserializationConfig().isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES))
                    log4jJsonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                //先转换为map对象
                map = log4jJsonObjectMapper.readValue(data, Map.class);
                //从map对象转换到log4jLogEvent
                log4jLogEvent = log4jJsonObjectMapper.convertValue(map, Log4jLogEvent.class);
                //custom properties的转换，由于map是LinkedHashMap，是有序的，因此可以通过遍历map获取custom properties
                boolean flag = false;
                for (Map.Entry<?, ?> entry : map.entrySet()) {
                    //通过key=project获取custom properties，因此custom properties必须要有project
                    if (entry.getKey().equals("project")) {
                        flag = true;
                    }
                    if (flag) {
                        //key必须为String
                        customProperties.put(entry.getKey().toString(), entry.getValue());
                    }
                }
            } catch (IOException e) {
                //本应logger.error("Log4j2转换失败", e);但为了防止处理异常的死循环，必须在本地输出
                System.out.println("Log4j2转换失败");
                e.printStackTrace();
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
