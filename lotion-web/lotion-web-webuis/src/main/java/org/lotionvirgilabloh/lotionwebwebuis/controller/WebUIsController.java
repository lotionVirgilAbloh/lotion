package org.lotionvirgilabloh.lotionwebwebuis.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class WebUIsController {

    /**
     * 静态资源地址
     */
    @Value("${lotion.web.static.url}")
    private String webStaticUrl;
    /**
     * host地址
     */
    @Value("${lotion.web.host.url}")
    private String webHostUrl;

    @Value("${lotion.web.eureka-url}")
    private String eurekaUrl;
    @Value("${lotion.web.hbase-url}")
    private String hbaseUrl;
    @Value("${lotion.web.hdfs-url}")
    private String hdfsUrl;
    @Value("${lotion.web.hystrix-url}")
    private String hystrixUrl;
    @Value("${lotion.web.kafka-url}")
    private String kafkaUrl;
    @Value("${lotion.web.spark-url}")
    private String sparkUrl;
    @Value("${lotion.web.yarn-url}")
    private String yarnUrl;
    @Value("${lotion.web.zookeeper-url}")
    private String zookeeperUrl;

    @RequestMapping("eureka")
    public String eureka(ModelMap modelMap) {
        modelMap.addAttribute("webStaticUrl", webStaticUrl);
        modelMap.addAttribute("webHostUrl", webHostUrl);
        modelMap.addAttribute("whichwebui", eurekaUrl);
        return "webuis";
    }

    @RequestMapping("hbase")
    public String hbase(ModelMap modelMap) {
        modelMap.addAttribute("webStaticUrl", webStaticUrl);
        modelMap.addAttribute("webHostUrl", webHostUrl);
        modelMap.addAttribute("whichwebui", hbaseUrl);
        return "webuis";
    }

    @RequestMapping("hdfs")
    public String hdfs(ModelMap modelMap) {
        modelMap.addAttribute("webStaticUrl", webStaticUrl);
        modelMap.addAttribute("webHostUrl", webHostUrl);
        modelMap.addAttribute("whichwebui", hdfsUrl);
        return "webuis";
    }

    @RequestMapping("hystrix")
    public String hystrix(ModelMap modelMap) {
        modelMap.addAttribute("webStaticUrl", webStaticUrl);
        modelMap.addAttribute("webHostUrl", webHostUrl);
        modelMap.addAttribute("whichwebui", hystrixUrl);
        return "webuis";
    }

    @RequestMapping("kafka")
    public String kafka(ModelMap modelMap) {
        modelMap.addAttribute("webStaticUrl", webStaticUrl);
        modelMap.addAttribute("webHostUrl", webHostUrl);
        modelMap.addAttribute("whichwebui", kafkaUrl);
        return "webuis";
    }

    @RequestMapping("yarn")
    public String yarn(ModelMap modelMap) {
        modelMap.addAttribute("webStaticUrl", webStaticUrl);
        modelMap.addAttribute("webHostUrl", webHostUrl);
        modelMap.addAttribute("whichwebui", yarnUrl);
        return "webuis";
    }

    @RequestMapping("zookeeper")
    public String zookeeper(ModelMap modelMap) {
        modelMap.addAttribute("webStaticUrl", webStaticUrl);
        modelMap.addAttribute("webHostUrl", webHostUrl);
        modelMap.addAttribute("whichwebui", zookeeperUrl);
        return "webuis";
    }

}
