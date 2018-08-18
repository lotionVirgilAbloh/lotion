package org.lotionvirgilabloh.lotionwebcontrol.configuration;


import org.apache.logging.log4j.ThreadContext;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

/**
 * 用于在加载配置文件时获取信息并通过log4j的ThreadContext发送至log4j，该listener必须利用application.addListeners注册，
 * 若以@Bean注册，则在Spring boot Autowired检测阶段才会注册，会错过ApplicationEnvironmentPreparedEvent事件产生
 */
public class Log4j2EnvironmentPreparedListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    /**
     * 防止接收到其他ApplicationEnvironmentPreparedEvent并刷新配置
     */
    private boolean setFlag =false;

    @Override
    public void onApplicationEvent(@SuppressWarnings("NullableProblems") ApplicationEnvironmentPreparedEvent applicationEnvironmentPreparedEvent) {
        if (!setFlag && applicationEnvironmentPreparedEvent.getEnvironment() != null) {
            MutablePropertySources mutablePropertySources = applicationEnvironmentPreparedEvent.getEnvironment().getPropertySources();
            setPropertySource(mutablePropertySources);
            setFlag = true;
        }
    }
    private void setPropertySource(MutablePropertySources mutablePropertySources) {
        //如果是config server的配置则PropertySource名称会不一样
        PropertySource<?> applicationConfigPropertySource = mutablePropertySources.get("applicationConfig: [classpath:/application.yml]");
        if (applicationConfigPropertySource != null && applicationConfigPropertySource.containsProperty("spring.application.name")) {
            String appName = (String) applicationConfigPropertySource.getProperty("spring.application.name");
            ThreadContext.put("app.name", appName);
        }
        if (applicationConfigPropertySource != null && applicationConfigPropertySource.containsProperty("spring.application.name")) {
            Integer serverPort = (Integer) applicationConfigPropertySource.getProperty("server.port");
            ThreadContext.put("server.port", serverPort.toString());
        }

        PropertySource<?> springCloudClientHostInfoPropertySource = mutablePropertySources.get("springCloudClientHostInfo");
        if (springCloudClientHostInfoPropertySource != null && springCloudClientHostInfoPropertySource.containsProperty("spring.cloud.client.ip-address")) {
            String serverIp = (String) springCloudClientHostInfoPropertySource.getProperty("spring.cloud.client.ip-address");
            ThreadContext.put("server.ip", serverIp);
        }
    }
}