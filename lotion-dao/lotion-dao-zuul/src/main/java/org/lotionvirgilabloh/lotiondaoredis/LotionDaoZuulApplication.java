package org.lotionvirgilabloh.lotiondaoredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableZuulProxy
public class LotionDaoZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(LotionDaoZuulApplication.class, args);
    }
}
