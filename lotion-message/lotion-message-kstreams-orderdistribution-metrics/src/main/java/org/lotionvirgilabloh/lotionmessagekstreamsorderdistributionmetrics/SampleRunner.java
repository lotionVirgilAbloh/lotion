package org.lotionvirgilabloh.lotionmessagekstreamsorderdistributionmetrics;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.SubscribableChannel;

public class SampleRunner {

    /**
     * OrderdistributionDashboardSinkConsumer
     * Logging the data when OrderdistributionDashboardSink received.
     */
    @EnableBinding(OrderdistributionDashboardSink.class)
    static class OrderdistributionDashboardSinkConsumer {

        private final Log logger = LogFactory.getLog(getClass());

        @StreamListener(OrderdistributionDashboardSink.INPUT)
        public void receive(String data) {
            logger.info("OrderdistributionDashboardSink data received..." + data);
        }
    }

    /**
     * Defining the OrderdistributionDashboardSink for Consumer to bind.
     */
    interface OrderdistributionDashboardSink {

        String INPUT = "inputDashboard";

        @Input(INPUT)
        SubscribableChannel inputDashboard();

    }
}
