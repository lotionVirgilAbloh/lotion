package org.lotionvirgilabloh.lotionwebgateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringCloudApplication
@Controller
public class LotionWebGatewayApplication {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        SpringApplication.run(LotionWebGatewayApplication.class, args);
    }

    @RequestMapping("/fallback")
    public @ResponseBody String fallback() {
        logger.error("产生了Hystrix断路");
        return "Error Occurred. Spring Cloud Gateway: Hystrix Fallback.";
    }

    @RequestMapping("/reject")
    public @ResponseBody String reject() {
        logger.error("拒绝路由");
        return "Reject.";
    }
}
