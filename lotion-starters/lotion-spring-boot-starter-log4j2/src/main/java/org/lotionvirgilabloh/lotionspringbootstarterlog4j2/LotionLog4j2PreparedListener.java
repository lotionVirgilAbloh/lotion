package org.lotionvirgilabloh.lotionspringbootstarterlog4j2;

import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.lookup.MainMapLookup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.*;

import java.util.*;

/**
 * AutoConfiguration注册的最早阶段监听{@link LotionLog4j2RefreshEvent} 事件，并对{@link ThreadContext}进行一次注入
 */
public class LotionLog4j2PreparedListener implements ApplicationListener<LotionLog4j2RefreshEvent> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String[] profiles;
    private boolean setBootstrap = true;
    private boolean setLocal = true;
    private boolean setCloud = true;
    private boolean setOrigin = true;
    private boolean hasDefault = false;
    private LinkedHashMap<String, String> setMain = new LinkedHashMap<>();
    private Set<String> appNames = new LinkedHashSet<>();
    private Set<String> customProperties = new LinkedHashSet<>();

    @Override
    public void onApplicationEvent(@SuppressWarnings("NullableProblems") LotionLog4j2RefreshEvent lotionLog4J2RefreshEvent) {
        Environment environment = lotionLog4J2RefreshEvent.getApplicationContext().getEnvironment();
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
     * 继承用于添加CustomProperties
     *
     * @return
     */
    private Set<String> setCustomProperties() {
        return new LinkedHashSet<>();
    }

    /**
     * 更新log4j2参数
     *
     * @param mutablePropertySources
     */
    private void setPropertySource(MutablePropertySources mutablePropertySources) throws ClassCastException {
        // Initialize AppNames
        if (appNames.size() == 0) {
            appNames.add("application");
        }
        // Set CustomProperties
        customProperties = setCustomProperties();
        // Get a List of OriginTrackedMapPropertySource
        List<OriginTrackedMapPropertySource> originTrackedMapPropertySources = new LinkedList<>();
        for (PropertySource<?> ps : mutablePropertySources) {
            if (ps.getName().startsWith("applicationConfig")) {
                originTrackedMapPropertySources.add((OriginTrackedMapPropertySource) ps);
            }

            if (setCloud && ps.getName().equals("springCloudClientHostInfo")) {
                String serverIp = (String) ps.getProperty("spring.cloud.client.ip-address");
                addSetMain(new Log4j2SetHelper("server.ip", new HashMap<String, String>() {
                    private static final long serialVersionUID = -800748987787071944L;

                    {
                        put("", serverIp);
                    }
                }));
                setCloud = false;
            }
        }
        // For Bootstrap
        if (setBootstrap) {
            boolean canSetBootstrap = false;
            Map<String, Log4j2SetHelper> log4j2SetHelperLinkedHashMap = new LinkedHashMap<>();
            for (OriginTrackedMapPropertySource o : originTrackedMapPropertySources) {
                if (o.getName().substring(17).contains("bootstrap")) {
                    canSetBootstrap = true;
                    processBootstrapMapPropertySource(o, log4j2SetHelperLinkedHashMap);
                    break;
                }
            }
            if (canSetBootstrap) {
                setBootstrap = false;
                for (Map.Entry<String, Log4j2SetHelper> entry : log4j2SetHelperLinkedHashMap.entrySet()) {
                    addSetMain(entry.getValue());
                }
            }
        }
        // For Local
        if (setLocal) {
            boolean canSetLocal = false;
            Map<String, Log4j2SetHelper> log4j2SetHelperLinkedHashMap = new LinkedHashMap<>();
            for (OriginTrackedMapPropertySource o : originTrackedMapPropertySources) {
                if (o.getName().substring(17).contains("application")) {
                    canSetLocal = true;
                    processMapPropertySource(o, log4j2SetHelperLinkedHashMap);
                }
            }
            if (canSetLocal) {
                setBootstrap = false;
                setLocal = false;
                for (Map.Entry<String, Log4j2SetHelper> entry : log4j2SetHelperLinkedHashMap.entrySet()) {
                    addSetMain(entry.getValue());
                }
            }
        }
        // For Origin
        for (PropertySource<?> ps : mutablePropertySources) {
            if (setOrigin && ps.getName().equals("bootstrapProperties")) {
                //若有config server的配置则以config server的配置为主
                if (setBootstrap || setLocal) {
                    setBootstrap = false;
                    setLocal = false;
                }
                List<MapPropertySource> gitMapPropertySource = getGitMapPropertySource((CompositePropertySource) ps);
                Map<String, Log4j2SetHelper> log4j2SetHelperLinkedHashMap = new LinkedHashMap<>();
                for (MapPropertySource mapPropertySource : gitMapPropertySource) {
                    processMapPropertySource(mapPropertySource, log4j2SetHelperLinkedHashMap);
                }
                for (Map.Entry<String, Log4j2SetHelper> entry : log4j2SetHelperLinkedHashMap.entrySet()) {
                    addSetMain(entry.getValue());
                }
                setOrigin = false;
            }
        }
        // Set Properties
        setLog4j2PropertiesViaThreadContext(setMain);
    }

    private LinkedList<MapPropertySource> getGitMapPropertySource(CompositePropertySource bootstrapPropertiesPropertySource) {
        LinkedList<MapPropertySource> gitMapPropertySource = new LinkedList<>();
        Iterator<PropertySource<?>> bootstrapPropertiesIterator = bootstrapPropertiesPropertySource.getPropertySources().iterator();
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
        return gitMapPropertySource;
    }

    private void processMapPropertySource(MapPropertySource mapPropertySource, Map<String, Log4j2SetHelper> log4j2SetHelperLinkedHashMap) throws ClassCastException {
        //若该配置文件不在已激活的profiles中，则跳过，否则执行修改
        Map<String, Object> shouldAlterReturns = shouldAlter(mapPropertySource);
        if (!((boolean) shouldAlterReturns.get("shouldAlter")))
            return;
        List<String> psProfiles = (List<String>) shouldAlterReturns.getOrDefault("profiles", new ArrayList<String>());
        if (mapPropertySource.containsProperty("spring.application.name")) {
            String appName = getProperty("spring.application.name", mapPropertySource).toString();
            setLog4j2SetHelper(log4j2SetHelperLinkedHashMap, "app.name", appName, psProfiles);
            appNames.add(appName);
        }
        if (mapPropertySource.containsProperty("server.port")) {
            String serverPort = getProperty("server.port", mapPropertySource).toString();
            setLog4j2SetHelper(log4j2SetHelperLinkedHashMap, "server.port", serverPort, psProfiles);
        }
        for (String property : customProperties) {
            if (mapPropertySource.containsProperty(property)) {
                String propertyValue = getProperty(property, mapPropertySource).toString();
                setLog4j2SetHelper(log4j2SetHelperLinkedHashMap, property, propertyValue, psProfiles);
            }
        }
    }

    private void processBootstrapMapPropertySource(MapPropertySource mapPropertySource, Map<String, Log4j2SetHelper> log4j2SetHelperLinkedHashMap) {
        if (mapPropertySource.containsProperty("spring.application.name")) {
            String appName = getProperty("spring.application.name", mapPropertySource).toString();
            setLog4j2SetHelper(log4j2SetHelperLinkedHashMap, "app.name", appName, new LinkedList<String>() {
                private static final long serialVersionUID = -509786057782619142L;

                {
                    add("");
                }
            });
            appNames.add(appName);
        }
        if (mapPropertySource.containsProperty("server.port")) {
            String serverPort = getProperty("server.port", mapPropertySource).toString();
            setLog4j2SetHelper(log4j2SetHelperLinkedHashMap, "server.port", serverPort, new LinkedList<String>() {
                private static final long serialVersionUID = -7331133441183959950L;

                {
                    add("");
                }
            });
        }
        for (String property : customProperties) {
            if (mapPropertySource.containsProperty(property)) {
                String propertyValue = getProperty(property, mapPropertySource).toString();
                setLog4j2SetHelper(log4j2SetHelperLinkedHashMap, property, propertyValue, new LinkedList<String>() {
                    private static final long serialVersionUID = -8178542040751699896L;

                    {
                        add("");
                    }
                });
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T getProperty(String propertyName, MapPropertySource mapPropertySource) {
        T ret;
        try {
            ret = (T) mapPropertySource.getProperty(propertyName);
        } catch (Exception e) {
            ret = null;
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

    private void setLog4j2SetHelper(Map<String, Log4j2SetHelper> log4j2SetHelperLinkedHashMap, String propertyName, String setString, List<String> psProfiles) {
        if (!log4j2SetHelperLinkedHashMap.containsKey(propertyName))
            log4j2SetHelperLinkedHashMap.put(propertyName, new Log4j2SetHelper(propertyName, new LinkedHashMap<>()));
        for (String profile : psProfiles) {
            log4j2SetHelperLinkedHashMap.get(propertyName).getSetter().put(profile, setString);
        }
    }

    private void addSetMain(Log4j2SetHelper log4j2SetHelper) {
        if (log4j2SetHelper.getSetter() == null) {
            return;
        }
        if (log4j2SetHelper.getSetter().size() == 0) {
            return;
        }
        StringBuilder setString = new StringBuilder();
        if (log4j2SetHelper.getSetter().size() == 1) {
            for (Map.Entry entry : log4j2SetHelper.getSetter().entrySet()) {
                setString.append(entry.getValue());
            }
        } else {
            //检测values是否均相同
            boolean valueFlag = true;
            String tempString = null;
            for (String s : log4j2SetHelper.getSetter().values()) {
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
                setString.append(log4j2SetHelper.getSetter().values().iterator().next());
            } else {
                //否则构造json字符串
                setString.append("{");
                for (Map.Entry s : log4j2SetHelper.getSetter().entrySet()) {
                    setString.append(s.getKey()).append(":").append(s.getValue()).append(",");
                }
                setString.setLength(setString.length() - 1);
                setString.append("}");
            }
        }
        setMain.put(log4j2SetHelper.getKey(), setString.toString());
    }

    private void setLog4j2PropertiesViaThreadContext(LinkedHashMap<String, String> setMain) {
        ThreadContext.putAll(setMain);
        logger.info("LotionLog4j2PreparedListener:ThreadContext.putAll({" + setMain.toString() + "})");
    }

    @Deprecated
    private void setLog4j2PropertiesViaMainMapLookup(LinkedHashMap<String, String> setMain) {
        String[] setArray = new String[(setMain.size() * 2)];
        int i = 0;
        for (Map.Entry<String, String> entry : setMain.entrySet()) {
            setArray[i++] = entry.getKey();
            setArray[i++] = entry.getValue();
        }
        MainMapLookup.setMainArguments(setArray);
        logger.info("LotionLog4j2PreparedListener:MainMapLookup.setMainArguments({" + setMain.toString() + "})");
    }
}
