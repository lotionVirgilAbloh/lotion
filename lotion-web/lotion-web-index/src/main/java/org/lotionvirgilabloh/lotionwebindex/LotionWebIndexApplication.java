package org.lotionvirgilabloh.lotionwebindex;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringCloudApplication
@EnableFeignClients
//@Controller
public class LotionWebIndexApplication {
    public static void main(String[] args) {
        SpringApplication.run(LotionWebIndexApplication.class, args);
    }

/*    @Value("${custom.abc}")
    String abcd;*/

/*    @RequestMapping("/abc")
    public @ResponseBody String abc() {
        return abcd;
    }*/
}
