package org.lotionvirgilabloh.lotionmessagebus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@RemoteApplicationEventScan(basePackages = "org.lotionvirgilabloh.lotionmessagebusevent")
public class LotionMessageBusApplication {

    public static void main(String[] args) {
        SpringApplication.run(LotionMessageBusApplication.class, args);
    }
}
