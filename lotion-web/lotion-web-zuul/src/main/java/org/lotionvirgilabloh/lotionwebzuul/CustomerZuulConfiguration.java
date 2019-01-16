package org.lotionvirgilabloh.lotionwebzuul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties(ZuulProperties.class)
public class CustomerZuulConfiguration {
    @Autowired
    private ServerProperties serverProperties;
    @Autowired
    private ZuulProperties zuulProperties;
    //创建自定义的RouteLocator,再用到的地方发送事件
    @Bean
    public RouteLocator routeLocator (){
        return new CustomerRouteLocator(serverProperties.getServlet ().getServletPrefix (),zuulProperties);
    }
}