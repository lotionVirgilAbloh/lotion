package org.lotionvirgilabloh.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

@EnableFeignClients
@EnableAuthorizationServer
@SpringCloudApplication
@SessionAttributes("authorizationRequest")
@RestController
public class LotionAuthcenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(LotionAuthcenterApplication.class, args);
	}

}
