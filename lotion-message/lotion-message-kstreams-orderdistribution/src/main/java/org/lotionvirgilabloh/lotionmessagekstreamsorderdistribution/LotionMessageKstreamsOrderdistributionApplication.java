package org.lotionvirgilabloh.lotionmessagekstreamsorderdistribution;

import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableEurekaClient
@RemoteApplicationEventScan(basePackages = "org.lotionVirgilAbloh.lotionbase.event")
public class LotionMessageKstreamsOrderdistributionApplication {
    public static final Log logger = LogFactory.getLog(LotionMessageKstreamsOrderdistributionApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LotionMessageKstreamsOrderdistributionApplication.class, args);
    }

    @EnableBinding(OrderdistributionProcessor.class)
    public static class OrderdistributionApplication {
        Gson gson = new Gson();

        @StreamListener(OrderdistributionProcessor.INPUTRAW)
        @SendTo({OrderdistributionProcessor.OUTPUTPROCESSED, OrderdistributionProcessor.OUTPUTMETRICS})
        public KStream<?, String>[] process(KStream<Object, String> input) {

            KStream<?, Order> kStream = input.map((k,v) -> new KeyValue<>(null, gson.fromJson(v, Order.class)))
                    //.peek((k, v) -> logger.info("Order :" + v.getOrder() + " ,GenerateTime:" + v.getGenerateTime()))
                    .map((k,v) -> {
                        v.setDistributedTime(System.currentTimeMillis());
                        return new KeyValue<>(null, v);
                    });

            KStream<?, String> metrics = kStream.map((k,v) -> new KeyValue<>(null, Long.toString(v.getDistributedTime() - v.getGenerateTime())));

            KStream<?, String> processed = kStream.map((k,v) -> new KeyValue<>(null, gson.toJson(v)));

            KStream<?, String>[] outputs = new KStream[2];
            outputs[0] = processed;
            outputs[1] = metrics;

            return outputs;
        }

    }

    /**
     * 用于将orderdistribution任务的度量进行统计，得到1分钟内任务执行平均耗时和数量，并输出到orderdistribution-dashboard等待订阅
     * 可以说是很不确定了，并不会写啊呀
     */
    /*@EnableBinding(MetricsProcessor.class)
    public static class OrderdistributionMetricsApplication {

        @StreamListener(MetricsProcessor.INPUTMETRICS)
        @SendTo(MetricsProcessor.OUTPUTDASHBOARD)
        public KStream<?, String> process(KStream<Object, String> input) {

            TimeWindowedKStream<String, Long> timeWindowedKStream = input.map((k, v) -> new KeyValue<String, Long>("orderdistribution", Long.valueOf(v)))
                    .groupByKey().windowedBy(TimeWindows.of(TimeUnit.MINUTES.toMillis(1)));

            KTable<Windowed<String>, Long> kTableAgg = timeWindowedKStream.aggregate(()->0L
                    ,(aggKey, newValue, aggValue)-> (newValue.longValue() + aggValue.longValue()) / 2
                    ,Materialized.<String, Long, WindowStore<Bytes, byte[]>>as("orderdistribution-agg-snapshot").withKeySerde(Serdes.String()).
                            withValueSerde(Serdes.Long()));

            KTable<Windowed<String>, Long> kTableCount = timeWindowedKStream.count();

            KTable<Windowed<String>, String> kTableJoined = kTableAgg.join(kTableCount, (lValue, rValue) -> lValue.toString() + "," + rValue.toString());

            KStream<?, String> result = kTableJoined.toStream().map((k, v) -> new KeyValue<>(null, v));

            return result;
        }
    }*/


    public interface OrderdistributionProcessor {

        String INPUTRAW = "inputRaw";
        @Input(INPUTRAW)
        KStream<?, ?> inputRaw();

        String OUTPUTMETRICS = "outputMetrics";
        @Output(OUTPUTMETRICS)
        KStream<?, ?> outputMetrics();

        String OUTPUTPROCESSED = "outputProcessed";
        @Output(OUTPUTPROCESSED)
        KStream<?, ?> outputProcessed();
    }
}
