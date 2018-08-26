package org.lotionvirgilabloh.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.web.bind.annotation.SessionAttributes;

@ComponentScan
@EnableAutoConfiguration
@EnableFeignClients
@EnableAuthorizationServer
@SpringCloudApplication
@SessionAttributes("authorizationRequest")
public class AuthserverApplication{

	public static void main(String[] args) {
		SpringApplication.run(AuthserverApplication.class, args);
	}
	

}
