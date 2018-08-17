package org.lotionvirgilabloh.lotionwebcontrol.configuration;


import org.apache.logging.log4j.ThreadContext;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

/**
 * 用于在加载配置文件时获取信息并通过log4j的ThreadContext发送至log4j，该listener必须在application注册，
 * 若在@Configuration中注册，会慢于ApplicationEnvironmentPreparedEvent事件产生，导致错过
 */
public class ApplicationEnvironmentPreparedListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
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
            String ipAddress = (String) springCloudClientHostInfoPropertySource.getProperty("spring.cloud.client.ip-address");
            ThreadContext.put("ip-address", ipAddress);
        }
    }
}