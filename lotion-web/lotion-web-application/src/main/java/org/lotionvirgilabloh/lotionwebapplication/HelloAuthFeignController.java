package org.lotionvirgilabloh.lotionwebapplication;

import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.json.GsonFactoryBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by umiderrick on 2019/3/8.
 */
@Import(FeignConfig.class)
@RestController
public class HelloAuthFeignController {

    @Autowired
    private HelloZuulService helloZuulService;



    @RequestMapping("test")
    String vv(){
        return helloZuulService.getHealth();
    }

}
