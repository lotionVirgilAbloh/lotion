package org.lotionvirgilabloh.sso;

import org.lotionvirgilabloh.sso.service.AuthCenterManager;
import org.lotionvirgilabloh.sso.util.JsonUtils;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.web.bind.annotation.RequestMapping;
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
