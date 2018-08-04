package org.lotionvirgilabloh.lotionconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class LotionConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(LotionConfigApplication.class, args);
    }
}
