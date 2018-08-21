package org.lotionvirgilabloh.lotionwebcontrol;

import org.lotionvirgilabloh.lotionspringbootstarterlog4j2.EnableLotionLog4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringCloudApplication
@EnableFeignClients
public class LotionWebControlApplication {

    public static void main(String[] args) {
        SpringApplication.run(LotionWebControlApplication.class, args);
    }
}
