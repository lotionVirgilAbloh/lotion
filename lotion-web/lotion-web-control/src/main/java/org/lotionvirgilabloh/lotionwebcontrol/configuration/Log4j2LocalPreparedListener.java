package org.lotionvirgilabloh.lotionwebcontrol.configuration;


import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

/**
 * 用于在加载配置文件时获取信息并通过log4j的ThreadContext将自定义参数传输至log4j
 * <p>
 * Log4j2LocalPreparedListener必须利用application.addListeners注册
 * 若以@Component等注册，则在Spring boot Autowired检测阶段才会注册，会错过ApplicationEnvironmentPreparedEvent事件产生
 */
public class Log4j2LocalPreparedListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 防止接收到其他ApplicationEnvironmentPreparedEvent并刷新配置
     */
    private boolean setFlag = false;

    @Override
    public void onApplicationEvent(@SuppressWarnings("NullableProblems") ApplicationEnvironmentPreparedEvent applicationEnvironmentPreparedEvent) {
        if (!setFlag && applicationEnvironmentPreparedEvent.getEnvironment() != null) {
            MutablePropertySources mutablePropertySources = applicationEnvironmentPreparedEvent.getEnvironment().getPropertySources();
            setPropertySource(mutablePropertySources);
            setFlag = true;
        }
    }

    /**
     * 如果配置文件来自config server的配置则交由Log4j2PreparedListener处理
     * 此处仅获取本地application.yml文件来更新log4j2参数
     * TODO:可以通过获取本地所有配置文件（带profile)后，选择生效的配置文件，待研究，在此先以application.yml文件为主
     *
     * @param mutablePropertySources
     */
    private void setPropertySource(MutablePropertySources mutablePropertySources) {
        PropertySource<?> applicationConfigPropertySource = mutablePropertySources.get("applicationConfig: [classpath:/application.yml]");
        if (applicationConfigPropertySource != null && applicationConfigPropertySource.containsProperty("spring.application.name")) {
            String appName = (String) applicationConfigPropertySource.getProperty("spring.application.name");
            ThreadContext.put("app.name", appName);
            logger.info("Log4j2LocalPreparedListener:log4j2重设参数:{key=app.name,value=" + appName + "}");
        }
        if (applicationConfigPropertySource != null && applicationConfigPropertySource.containsProperty("server.port")) {
            Integer serverPort = (Integer) applicationConfigPropertySource.getProperty("server.port");
            if (serverPort != null) {
                ThreadContext.put("server.port", serverPort.toString());
                logger.info("Log4j2LocalPreparedListener:log4j2重设参数:{key=server.port,value=" + serverPort.toString() + "}");
            }
        }

        PropertySource<?> springCloudClientHostInfoPropertySource = mutablePropertySources.get("springCloudClientHostInfo");
        if (springCloudClientHostInfoPropertySource != null && springCloudClientHostInfoPropertySource.containsProperty("spring.cloud.client.ip-address")) {
            String serverIp = (String) springCloudClientHostInfoPropertySource.getProperty("spring.cloud.client.ip-address");
            ThreadContext.put("server.ip", serverIp);
            logger.info("Log4j2LocalPreparedListener:log4j2重设参数:{key=server.ip,value=" + serverIp + "}");
        }
    }
}