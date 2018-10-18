package org.lotionvirgilabloh.lotionwebindex;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringCloudApplication
@EnableFeignClients
public class LotionWebIndexApplication {
    public static void main(String[] args) {
        SpringApplication.run(LotionWebIndexApplication.class, args);
    }
}
