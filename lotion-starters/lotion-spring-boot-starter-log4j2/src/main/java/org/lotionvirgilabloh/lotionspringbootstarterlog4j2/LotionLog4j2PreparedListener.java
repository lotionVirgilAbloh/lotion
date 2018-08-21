package org.lotionvirgilabloh.lotionspringbootstarterlog4j2;

import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.*;

import java.util.*;

/**
 * 用于接收ApplicationPreparedEvent并获取其中配置来刷新log4j2的自定义配置
 * <p>
 * Log4j2ConfigServerPreparedListener可以利用@Component注册
 * 在启动阶段Spring boot会向config server fetch 配置文件3次，在第二次时Log4j2PreparedListener就已经注册并监听ApplicationPreparedEvent事件
 * 因此无须利用application.addListeners注册
 */
public class LotionLog4j2PreparedListener implements ApplicationListener<ApplicationPreparedEvent> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String[] profiles;
    private boolean setBootstrap = true;
    private boolean setLocal = true;
    private boolean setCloud = true;
    private boolean setOrigin = true;
    private boolean hasDefault = false;
    private Set<String> appNames = new LinkedHashSet<>();

    @Override
    public void onApplicationEvent(@SuppressWarnings("NullableProblems") ApplicationPreparedEvent applicationPreparedEvent) {
        Environment environment = applicationPreparedEvent.getApplicationContext().getEnvironment();
        MutablePropertySources mutablePropertySources = ((ConfigurableEnvironment) environment).getPropertySources();
        if (environment.getActiveProfiles().length > 0) {
            profiles = environment.getActiveProfiles();
        } else {
            profiles = environment.getDefaultProfiles();
        }
        hasDefault = false;
        for (String profile : profiles) {
            if (profile.equals("default")) {
                hasDefault = true;
                break;
            }
        }
        if ((setBootstrap || setLocal || setCloud || setOrigin) && profiles.length > 0)
            setPropertySource(mutablePropertySources);
    }

    /**
     * 更新log4j2参数
     *
     * @param mutablePropertySources
     */
    public void setPropertySource(MutablePropertySources mutablePropertySources) throws ClassCastException {
        if (appNames.size() == 0) {
            appNames.add("application");
        }
        List<OriginTrackedMapPropertySource> originTrackedMapPropertySources = new LinkedList<>();
        for (PropertySource<?> ps : mutablePropertySources) {
            if (ps.getName().startsWith("applicationConfig")) {
                originTrackedMapPropertySources.add((OriginTrackedMapPropertySource) ps);
            }

            if (setCloud && ps.getName().equals("springCloudClientHostInfo")) {
                String serverIp = (String) ps.getProperty("spring.cloud.client.ip-address");
                ThreadContext.put("server.ip", serverIp);
                logger.info("LotionLog4j2PreparedListener:log4j2重设参数:{key:" + "server.ip" + ",value:" + serverIp + "}");
                setCloud = false;
            }
        }
        //for bootstrap
        if (setBootstrap) {
            for (OriginTrackedMapPropertySource o : originTrackedMapPropertySources) {
                if (o.getName().substring(17).contains("bootstrap")) {
                    processBootstrapMapPropertySource(o);
                    setBootstrap = false;
                    break;
                }
            }
        }
        //for local
        if (setLocal) {
            boolean canSetLocal = false;
            Map<String, ThreadContextSetHelper> threadContextSetHelperLinkedHashMap = new LinkedHashMap<>();
            for (OriginTrackedMapPropertySource o : originTrackedMapPropertySources) {
                if (o.getName().substring(17).contains("application")) {
                    canSetLocal = true;
                    processMapPropertySource(o, threadContextSetHelperLinkedHashMap);
                }
            }
            if (canSetLocal) {
                setBootstrap = false;
                setLocal = false;
                Set<String> newAppNames = new LinkedHashSet<>();
                for (Map.Entry<String, ThreadContextSetHelper> entry : threadContextSetHelperLinkedHashMap.entrySet()) {
                    if (entry.getValue().getSetter().get("app.name") != null) {
                        newAppNames.add(entry.getValue().getSetter().get("app.name"));
                    }
                    setThreadContextMapProperty(entry.getValue());
                }
                appNames.addAll(newAppNames);
            }
        }
        //for origin
        for (PropertySource<?> ps : mutablePropertySources) {
            if (setOrigin && ps.getName().equals("bootstrapProperties")) {
                //若有config server的配置则以config server的配置为主
                if (setBootstrap || setLocal) {
                    setBootstrap = false;
                    setLocal = false;
                }
                CompositePropertySource bootstrapPropertiesPropertySource = (CompositePropertySource) ps;
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
                Map<String, ThreadContextSetHelper> threadContextSetHelperLinkedHashMap = new LinkedHashMap<>();
                for (MapPropertySource mapPropertySource : gitMapPropertySource) {
                    processMapPropertySource(mapPropertySource, threadContextSetHelperLinkedHashMap);
                }
                Set<String> newAppNames = new LinkedHashSet<>();
                for (Map.Entry<String, ThreadContextSetHelper> entry : threadContextSetHelperLinkedHashMap.entrySet()) {
                    if (entry.getValue().getSetter().get("app.name") != null) {
                        newAppNames.add(entry.getValue().getSetter().get("app.name"));
                    }
                    setThreadContextMapProperty(entry.getValue());
                }
                appNames.addAll(newAppNames);
                setOrigin = false;
            }
        }
    }

    private void processMapPropertySource(MapPropertySource mapPropertySource, Map<String, ThreadContextSetHelper> threadContextSetHelperLinkedHashMap) throws ClassCastException {
        //若该配置文件不在已激活的profiles中，则跳过，否则执行修改
        Map<String, Object> shouldAlterReturns = shouldAlter(mapPropertySource);
        if (!((boolean) shouldAlterReturns.get("shouldAlter")))
            return;
        List<String> psProfiles = (List<String>) shouldAlterReturns.getOrDefault("profiles", new ArrayList<String>());
        if (mapPropertySource.containsProperty("spring.application.name")) {
            String appName = (String) mapPropertySource.getProperty("spring.application.name");
            setThreadContextHelper(threadContextSetHelperLinkedHashMap, "app.name", appName, psProfiles);
        }
        if (mapPropertySource.containsProperty("server.port")) {
            String serverPort = getPropertyMaybeInteger("server.port", mapPropertySource);
            setThreadContextHelper(threadContextSetHelperLinkedHashMap, "server.port", serverPort, psProfiles);
        }
    }

    private void processBootstrapMapPropertySource(MapPropertySource mapPropertySource) {
        if (mapPropertySource.containsProperty("spring.application.name")) {
            String appName = (String) mapPropertySource.getProperty("spring.application.name");
            ThreadContext.put("app.name", appName);
            appNames.add(appName);
            logger.info("LotionLog4j2PreparedListener:log4j2重设参数:{key:" + "app.name" + ",value:" + appName + "}");
        }
        if (mapPropertySource.containsProperty("server.port")) {
            String serverPort = getPropertyMaybeInteger("server.port", mapPropertySource);
            ThreadContext.put("server.port", serverPort);
            logger.info("LotionLog4j2PreparedListener:log4j2重设参数:{key:" + "server.port" + ",value:" + serverPort + "}");
        }
    }

    private String getPropertyMaybeInteger(String propertyName, MapPropertySource mapPropertySource) {
        String ret;
        try {
            ret = (String) mapPropertySource.getProperty(propertyName);
        } catch (ClassCastException e) {
            Integer integer = (Integer) mapPropertySource.getProperty(propertyName);
            if (integer != null) {
                ret = integer.toString();
            } else {
                ret = "";
            }
        }
        return ret;
    }

    private Map<String, Object> shouldAlter(MapPropertySource mapPropertySource) {
        Map<String, Object> mapReturns = new LinkedHashMap<>();
        String mapPropertySourceName = mapPropertySource.getName();
        int offsetSlash = mapPropertySourceName.lastIndexOf("/");
        int offsetDot = mapPropertySourceName.lastIndexOf(".");
        if (offsetSlash >= offsetDot || offsetSlash < 0) {
            mapReturns.put("shouldAlter", false);
            return mapReturns;
        }
        String propertyFilename = mapPropertySourceName.substring(offsetSlash + 1, offsetDot);

        //根据profiles进行过滤
        Set<String> profileSet = new LinkedHashSet<>();
        mapPropertySource.getSource().forEach((key, value) -> {
            if (key.startsWith("spring.profiles")) {
                profileSet.add(String.valueOf(value));
            }
        });
        List<String> matchedProfiles = new ArrayList<>();
        if (profileSet.size() == 0) {
            //若该文档不包含spring.profiles参数则根据applicationName进行过滤
            for (String appName : appNames) {
                if (hasDefault && propertyFilename.equals(appName)) {
                    if (!mapReturns.containsKey("shouldAlter"))
                        mapReturns.put("shouldAlter", true);
                    matchedProfiles.add("default");
                    break;
                } else if (propertyFilename.contains(appName)) {
                    String propertyProfile = propertyFilename.substring(appName.length());
                    for (String profile : profiles) {
                        if (propertyProfile.contains(profile)) {
                            if (!mapReturns.containsKey("shouldAlter"))
                                mapReturns.put("shouldAlter", true);
                            matchedProfiles.add(profile);
                            break;
                        }
                    }
                }
            }
        } else {
            for (String profile : profiles) {
                for (String s : profileSet) {
                    if (profile.equals(s)) {
                        if (!mapReturns.containsKey("shouldAlter"))
                            mapReturns.put("shouldAlter", true);
                        matchedProfiles.add(profile);
                        break;
                    }
                }
            }
        }
        mapReturns.put("profiles", matchedProfiles);
        if (!mapReturns.containsKey("shouldAlter"))
            mapReturns.put("shouldAlter", false);
        return mapReturns;
    }

    private void setThreadContextHelper(Map<String, ThreadContextSetHelper> threadContextSetHelperLinkedHashMap, String propertyName, String setString, List<String> psProfiles) {
        if (!threadContextSetHelperLinkedHashMap.containsKey(propertyName))
            threadContextSetHelperLinkedHashMap.put(propertyName, new ThreadContextSetHelper(propertyName, new LinkedHashMap<>()));
        for (String profile : psProfiles) {
            threadContextSetHelperLinkedHashMap.get(propertyName).getSetter().put(profile, setString);
        }
    }

    private void setThreadContextMapProperty(ThreadContextSetHelper threadContextSetHelper) {
        if (threadContextSetHelper.getSetter() == null) {
            return;
        }
        if (threadContextSetHelper.getSetter().size() == 0) {
            return;
        }
        StringBuilder setString = new StringBuilder();
        if (threadContextSetHelper.getSetter().size() == 1) {
            for (Map.Entry entry : threadContextSetHelper.getSetter().entrySet()) {
                setString.append(entry.getValue());
            }
        } else {
            //检测values是否均相同
            boolean valueFlag = true;
            String tempString = null;
            for (String s : threadContextSetHelper.getSetter().values()) {
                if (tempString == null) {
                    tempString = s;
                } else {
                    if (!s.equals(tempString)) {
                        valueFlag = false;
                        break;
                    }
                }
            }
            //若profiles的value均相同则合并
            if (valueFlag) {
                setString.append(threadContextSetHelper.getSetter().values().iterator().next());
            } else {
                //否则构造json字符串
                setString.append("{");
                for (Map.Entry s : threadContextSetHelper.getSetter().entrySet()) {
                    setString.append(s.getKey()).append(":").append(s.getValue()).append(",");
                }
                setString.setLength(setString.length() - 1);
                setString.append("}");
            }
        }
        ThreadContext.put(threadContextSetHelper.getKey(), setString.toString());
        logger.info("LotionLog4j2PreparedListener:log4j2重设参数:{key:" + threadContextSetHelper.getKey() + ",value:" + setString.toString() + "}");
    }
}
