package org.lotionvirgilabloh.lotionmessagekafka;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.GenericMessage;

import java.util.Random;

/**
 * This is a template application for Spring Boot for Apache Kafka to generate Kafka's Producers or Consumers.
 * Here's a example with one Producer and two Consumers to consume.
 * You can see "application.yml" for configuration.
 * Tested.
 */
@SpringBootApplication
@EnableEurekaClient
public class LotionMessageKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(LotionMessageKafkaApplication.class, args);
    }

    /**
     * TestProducer1
     * Generating random words and sending them to the topic "lotion-message-kafka-test".
     */
    @EnableBinding(TestSource1.class)
    static class TestProducer1 {
        private String[] randomWords = new String[]{"a", "b", "c", "d", "e"};
        private Random random = new Random();

        /**
         * This method can send data to topic at a rate as one word per second.
         * @return GenericMessage
         */
        @Bean
        @InboundChannelAdapter(channel = TestSource1.OUTPUT, poller = @Poller(fixedDelay = "1000"))
        public MessageSource<String> sendTestData() {
            return () -> {
                int idx = random.nextInt(5);
                return new GenericMessage<>(randomWords[idx]);
            };
        }
    }

    /**
     * TestConsumer1
     * Logging the data when TestConsumer1 received.
     */
    @EnableBinding(TestSink1.class)
    static class TestConsumer1 {

        private final Log logger = LogFactory.getLog(getClass());

        @StreamListener(TestSink1.INPUT)
        public void receive(String data) {
            logger.info("Consumer1 data received..." + data);
        }
    }

    /**
     * TestConsumer2
     * Logging the data when TestConsumer2 received.
     */
    @EnableBinding(TestSink2.class)
    static class TestConsumer2 {

        private final Log logger = LogFactory.getLog(getClass());

        @StreamListener(TestSink2.INPUT)
        public void receive(String data) {
            logger.info("Consumer2 data received..." + data);
        }
    }

    /**
     * Defining the output1 for Producer to bind.
     */
    interface TestSource1 {

        String OUTPUT = "output1";

        @Output(OUTPUT)
        MessageChannel output1();

    }

    /**
     * Defining the input1 for Consumer to bind.
     */
    interface TestSink1 {

        String INPUT = "input1";

        @Input(INPUT)
        SubscribableChannel input1();

    }

    /**
     * Defining the input2 for Consumer to bind.
     */
    interface TestSink2 {

        String INPUT = "input2";

        @Input(INPUT)
        SubscribableChannel input2();

    }

}
