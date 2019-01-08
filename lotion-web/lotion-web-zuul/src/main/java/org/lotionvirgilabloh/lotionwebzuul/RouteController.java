package org.lotionvirgilabloh.lotionwebzuul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Controller
public class RouteController {
    //spring默认的上下文事件发送器
    @Autowired
    public ApplicationEventPublisher publisher;
    @Autowired //注入我们自定义的路由规则加载器
    public RouteLocator routeLocator;

    /**
     * 加载所有可配置的路由规则
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/routes",method = RequestMethod.GET)
    public String listRoutes(){
        return RouteCache.routeMap.toString();
    }

    /**
     * 添加一条路由规则
     * @param serviceId
     * @param path
     * @param url
     * @param headers
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/routes/{serviceId}",method = RequestMethod.POST)
    public String addRoutes(@PathVariable String serviceId,
                            @RequestParam String path,
                            @RequestParam String url,
                            @RequestParam(required = false) String headers){
        ZuulProperties.ZuulRoute route = new ZuulProperties.ZuulRoute();
        route.setId(serviceId);
        route.setServiceId(serviceId);
        route.setUrl(url);
        route.setPath(path);
        if (headers != null && !"".equals(headers)) {
            Set<String> set = new HashSet<String> ();
            set.addAll(Arrays.asList(headers.split(",")));
            route.setSensitiveHeaders(set);
        }
        RouteCache.routeMap.put(path,route);

        refreshRoute();
        return route.toString();
    }

    /**
     * 删除指定的路由
     * @param serviceId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/routes/{serviceId}",method = RequestMethod.DELETE)
    public String delteRoutes(@PathVariable String serviceId){
        ZuulProperties.ZuulRoute route = null;
        for (ZuulProperties.ZuulRoute zuulRoute:RouteCache.routeMap.values()){
            if (serviceId.equals(zuulRoute.getServiceId())){
                route = zuulRoute;
                RouteCache.routeMap.remove(route.getPath());
                break;
            }
        }
        if (route == null) {
            return "未找到路由规则";
        }
        refreshRoute();
        return route.toString();
    }
    /**
     * 删除指定的路由
     * @param serviceId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/routes/{serviceId}",method = RequestMethod.PUT)
    public String updateRoutes(@PathVariable String serviceId,
                               @RequestParam String path,
                               @RequestParam String url,
                               @RequestParam String headers){
        ZuulProperties.ZuulRoute route = null;
        for (ZuulProperties.ZuulRoute zuulRoute:RouteCache.routeMap.values()){
            if (serviceId.equals(zuulRoute.getServiceId())){
                route = zuulRoute;
                RouteCache.routeMap.remove(route.getPath());
                break;
            }
        }
        if (route == null) {
            return "未找到路由规则";
        }else {
            route.setUrl(url);
            route.setPath(path);
            if (headers != null && !"".equals(headers)) {
                Set<String> set = new HashSet<String>();
                set.addAll(Arrays.asList(headers.split(",")));
                route.setSensitiveHeaders(set);
            }
        }
        refreshRoute();
        return route.toString();
    }

    /**
     * 发送事件，刷新路由规则
     */
    private void refreshRoute() {
        //创建路由事件
        RoutesRefreshedEvent event = new RoutesRefreshedEvent(routeLocator);
        //发送路由事件
        publisher.publishEvent(event);
    }
}
