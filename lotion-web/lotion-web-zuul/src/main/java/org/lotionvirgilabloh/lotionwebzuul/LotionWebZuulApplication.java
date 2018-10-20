package org.lotionvirgilabloh.lotionwebzuul;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringCloudApplication
@EnableZuulProxy
@Controller
public class LotionWebZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(LotionWebZuulApplication.class, args);
    }

    /**
     *  用于前后端分离开发，使得跨域请求可以被访问
     *
     * attention:简单跨域就是GET，HEAD和POST请求，但是POST请求的"Content-Type"只能是application/x-www-form-urlencoded, multipart/form-data 或 text/plain
     * 反之，就是非简单跨域，此跨域有一个预检机制，说直白点，就是会发两次请求，一次OPTIONS请求，一次真正的请求
     */
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
    @PreAuthorize("hasAuthority('adm')")
    @RequestMapping(value = "/testadmin")
    public @ResponseBody String testadmin() {
        return "admin ok";
    }

    @PreAuthorize("hasAuthority('root')")
    @RequestMapping(value = "/testroot")
    public @ResponseBody String testroot() {
        return "root ok";
    }

    @PreAuthorize("hasAuthority('root')")
    @RequestMapping(value = "/control")
    public  String control() {
        return "control";
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/log")
    public String log() {
        return "log";
    }

    @RequestMapping("/lotion/login")
    public String dashboard() {
        return "redirect:/#/";
    }


    @RequestMapping("/")
    public String login() {
        return "login";
    }
}
