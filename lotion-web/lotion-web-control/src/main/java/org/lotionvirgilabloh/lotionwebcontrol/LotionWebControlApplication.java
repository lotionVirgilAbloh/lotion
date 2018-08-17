package org.lotionvirgilabloh.lotionwebcontrol;

import org.lotionvirgilabloh.lotionwebcontrol.configuration.ApplicationEnvironmentPreparedListener;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringCloudApplication
@EnableFeignClients
public class LotionWebControlApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(LotionWebControlApplication.class);
        application.addListeners(new ApplicationEnvironmentPreparedListener());
        application.run(args);
    }
}
