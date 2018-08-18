package org.lotionvirgilabloh.lotionwebcontrol;

import org.lotionvirgilabloh.lotionwebcontrol.configuration.Log4j2LocalPreparedListener;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringCloudApplication
@EnableFeignClients
public class LotionWebControlApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(LotionWebControlApplication.class);
        application.addListeners(new Log4j2LocalPreparedListener());
        application.run(args);
    }
}
