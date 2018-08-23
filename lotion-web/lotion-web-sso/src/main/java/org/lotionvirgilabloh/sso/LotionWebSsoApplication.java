package org.lotionvirgilabloh.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringCloudApplication
@ComponentScan
public class LotionWebSsoApplication  {

    public static void main(String[] args) {
        SpringApplication.run(LotionWebSsoApplication.class, args);
    }

}
