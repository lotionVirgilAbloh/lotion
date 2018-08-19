package org.lotionvirgilabloh.lotionwebcontrol.configuration;

import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.*;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 用于接收ApplicationPreparedEvent并获取其中来自config server的配置文件来刷新log4j2的自定义配置
 * <p>
 * Log4j2ConfigServerPreparedListener可以利用@Component注册
 * 在启动阶段Spring boot会向config server fetch 配置文件3次，在第二次时Log4j2PreparedListener就已经注册并监听ApplicationPreparedEvent事件
 * 因此无须利用application.addListeners注册
 */
@Component
public class Log4j2ConfigServerPreparedListener implements ApplicationListener<ApplicationPreparedEvent> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 防止接收到其他ApplicationPreparedEvent并刷新配置
     */
    private boolean setFlag = false;

    @Override
    public void onApplicationEvent(@SuppressWarnings("NullableProblems") ApplicationPreparedEvent applicationPreparedEvent) {
        if (!setFlag) {
            MutablePropertySources mutablePropertySources = applicationPreparedEvent.getApplicationContext().getEnvironment().getPropertySources();
            setPropertySource(mutablePropertySources);
            setFlag = true;
        }
    }

    /**
     * 处理来自config server的配置文件，更新log4j2参数
     * TODO:不应该顺序遍历，应该只选取生效的配置文件，待继续研究，在此先以遍历为主
     *
     * @param mutablePropertySources
     */
    private void setPropertySource(MutablePropertySources mutablePropertySources) {
        CompositePropertySource bootstrapPropertiesPropertySource = (CompositePropertySource) mutablePropertySources.get("bootstrapProperties");
        if (bootstrapPropertiesPropertySource == null) {
            return;
        }
        Iterator<PropertySource<?>> bootstrapPropertiesIterator = bootstrapPropertiesPropertySource.getPropertySources().iterator();
        List<MapPropertySource> gitMapPropertySource = new LinkedList<>();
        while (bootstrapPropertiesIterator.hasNext()) {
            CompositePropertySource configServicePropertySource = (CompositePropertySource) bootstrapPropertiesIterator.next();
            if (configServicePropertySource.getName().equals("configService")) {
                for (PropertySource<?> propertySource : configServicePropertySource.getPropertySources()) {
                    MapPropertySource mapPropertySource = (MapPropertySource) propertySource;
                    if (mapPropertySource.getName().startsWith("git") || mapPropertySource.getName().startsWith("http")) {
                        gitMapPropertySource.add(mapPropertySource);
                    }
                }
                break;
            }
        }

        for (MapPropertySource mapPropertySource : gitMapPropertySource) {
            if (mapPropertySource != null && mapPropertySource.containsProperty("spring.application.name")) {
                String appName = (String) mapPropertySource.getProperty("spring.application.name");
                ThreadContext.put("app.name", appName);
                logger.info("Log4j2ConfigServerPreparedListener:log4j2重设参数:{key=app.name,value=" + appName + "}");
            }
            if (mapPropertySource != null && mapPropertySource.containsProperty("server.port")) {
                Integer serverPort = (Integer) mapPropertySource.getProperty("server.port");
                if (serverPort != null) {
                    ThreadContext.put("server.port", serverPort.toString());
                    logger.info("Log4j2ConfigServerPreparedListener:log4j2重设参数:{key=server.port,value=" + serverPort.toString() + "}");
                }
            }
        }
    }
}
