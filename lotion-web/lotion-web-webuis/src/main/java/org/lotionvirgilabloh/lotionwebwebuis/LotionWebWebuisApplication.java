package org.lotionvirgilabloh.lotionwebwebuis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringCloudApplication
@EnableFeignClients
public class LotionWebWebuisApplication {

    public static void main(String[] args) {
        SpringApplication.run(LotionWebWebuisApplication.class, args);
    }
}
