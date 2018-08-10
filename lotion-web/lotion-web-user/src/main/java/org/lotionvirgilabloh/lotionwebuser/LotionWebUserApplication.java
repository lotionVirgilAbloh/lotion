package org.lotionvirgilabloh.lotionwebuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringCloudApplication
@EnableEurekaClient
@EnableAuthorizationServer
@EnableFeignClients
public class LotionWebUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(LotionWebUserApplication.class, args);
    }
}
