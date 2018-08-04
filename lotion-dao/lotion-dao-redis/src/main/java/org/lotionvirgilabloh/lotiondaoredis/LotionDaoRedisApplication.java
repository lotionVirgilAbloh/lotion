package org.lotionvirgilabloh.lotiondaoredis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class LotionDaoRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(LotionDaoRedisApplication.class, args);
    }

    @Value("${server.port}")
    String port;
    @RequestMapping("/redishealth")
    public String home() {
        return "hi i'm redis,i am from port:" +port;
    }

}
