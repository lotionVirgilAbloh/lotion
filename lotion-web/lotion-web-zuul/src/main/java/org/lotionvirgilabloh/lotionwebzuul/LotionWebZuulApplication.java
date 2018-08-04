package org.lotionvirgilabloh.lotionwebzuul;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringCloudApplication
@EnableZuulProxy
public class LotionWebZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(LotionWebZuulApplication.class, args);
    }
}
