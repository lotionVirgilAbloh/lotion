package org.lotionvirgilabloh.lotionmessagekstreamsorderdistribution;

import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

import java.util.UUID;

public class SampleRunner {

    /**
     * OrderdistributionSourceProducer
     */
    @EnableBinding(OrderdistributionSource.class)
    static class OrderdistributionSourceProducer {
        Gson gson = new Gson();

        /**
         * Send data
         * @return GenericMessage
         */
        @Bean
        @InboundChannelAdapter(channel = OrderdistributionSource.OUTPUT, poller = @Poller(fixedDelay = "1000"))
        public MessageSource<String> sendTestData() {

            return () -> {
                Order order = new Order();
                order.setOrder(UUID.randomUUID().toString().replace("-", "").toLowerCase());
                order.setGenerateTime(System.currentTimeMillis());
                return new GenericMessage<>(gson.toJson(order));
            };
        }
    }

    /**
     * OrderdistributionProcessedSinkConsumer
     * Logging the data when OrderdistributionProcessedSink received.
     */
    @EnableBinding(OrderdistributionProcessedSink.class)
    static class OrderdistributionProcessedSinkConsumer {

        private final Log logger = LogFactory.getLog(getClass());

        @StreamListener(OrderdistributionProcessedSink.INPUT)
        public void receive(String data) {
            logger.info("OrderdistributionProcessedSink data received..." + data);
        }
    }

    /**
     * Defining the OrderdistributionProcessedSink for Consumer to bind.
     */
    interface OrderdistributionProcessedSink {

        String INPUT = "inputProcessed";

        @Input(INPUT)
        SubscribableChannel inputProcessed();

    }

    /**
     * Defining the OrderdistributionSource for Producer to bind.
     */
    interface OrderdistributionSource {

        String OUTPUT = "outputRaw";

        @Output(OUTPUT)
        MessageChannel outputRaw();

    }


}
