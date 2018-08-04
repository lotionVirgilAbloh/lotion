package org.lotionvirgilabloh.lotionmessagekstreamsorderdistributionmetrics;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;

@SpringBootApplication
public class LotionMessageKstreamsOrderdistributionMetricsApplication {
    public static final Log logger = LogFactory.getLog(LotionMessageKstreamsOrderdistributionMetricsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LotionMessageKstreamsOrderdistributionMetricsApplication.class, args);
    }

    @EnableBinding(OrderdistributionMetricsProcessor.class)
    public static class OrderdistributionMetricsApplication {
        @StreamListener(OrderdistributionMetricsProcessor.INPUTMETRICS)
        @SendTo(OrderdistributionMetricsProcessor.OUTPUTDASHBOARD)
        public KStream<?, String> process(KStream<Object, String> input) {
            input.peek((k,v) -> {
                logger.info(v);
            });
            TimeWindowedKStream<String, Long> timeWindowedKStream = input.map((k, v) -> new KeyValue<String, Long>("orderdistribution", Long.valueOf(v)))
                    .groupByKey(Serialized.with(Serdes.String(), Serdes.Long())).windowedBy(TimeWindows.of(30000));
            return timeWindowedKStream.count(Materialized.as("orderdistributionmetrics-snapshot")).toStream().map((k, v) -> new KeyValue<>(null, v.toString()));
        }
    }

    public interface OrderdistributionMetricsProcessor {

        String INPUTMETRICS = "inputMetrics";
        @Input(INPUTMETRICS)
        KStream<?, ?> inputMetrics();

        String OUTPUTDASHBOARD = "outputDashboard";
        @Output(OUTPUTDASHBOARD)
        KStream<?, ?> outputDashboard();
    }
}
