package org.lotionvirgilabloh.lotionweblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringCloudApplication
@EnableFeignClients
public class LotionWebLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(LotionWebLogApplication.class, args);
    }
}
