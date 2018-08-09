package org.lotionvirgilabloh.lotionwebuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringCloudApplication
public class LotionWebUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(LotionWebUserApplication.class, args);
    }
}
