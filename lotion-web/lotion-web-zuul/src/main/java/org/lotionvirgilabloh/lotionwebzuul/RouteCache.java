package org.lotionvirgilabloh.lotionwebzuul;

import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;

import java.util.LinkedHashMap;
import java.util.Map;

public class RouteCache {

    public static Map<String,ZuulProperties.ZuulRoute> routeMap = new LinkedHashMap<> ();
}
