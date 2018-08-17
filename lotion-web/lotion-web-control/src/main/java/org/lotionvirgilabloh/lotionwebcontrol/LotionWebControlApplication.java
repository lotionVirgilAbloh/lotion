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


    /**
     * 另一种解决Spring boot 2没有自动映射../actuator/hystrix.stream问题的方法
     *
     * @return
     */
    /*@Bean
    public ServletRegistrationBean getServlet(){

        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/actuator/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");

        return registrationBean;
    }*/
}
