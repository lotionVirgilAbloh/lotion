package org.lotionvirgilabloh.lotionwebwebuis.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class WebUIsController {

    @Value("${custom.eureka-url}")
    private String eurekaUrl;
    @Value("${custom.hbase-url}")
    private String hbaseUrl;
    @Value("${custom.hdfs-url}")
    private String hdfsUrl;
    @Value("${custom.hystrix-url}")
    private String hystrixUrl;
    @Value("${custom.kafka-url}")
    private String kafkaUrl;
    @Value("${custom.spark-url}")
    private String sparkUrl;
    @Value("${custom.yarn-url}")
    private String yarnUrl;
    @Value("${custom.zookeeper-url}")
    private String zookeeperUrl;

    @RequestMapping("eureka")
    public String eureka(ModelMap modelMap) {
        modelMap.addAttribute("whichwebui", eurekaUrl);
        return "webuis";
    }

    @RequestMapping("hbase")
    public String hbase(ModelMap modelMap) {
        modelMap.addAttribute("whichwebui", hbaseUrl);
        return "webuis";
    }

    @RequestMapping("hdfs")
    public String hdfs(ModelMap modelMap) {
        modelMap.addAttribute("whichwebui", hdfsUrl);
        return "webuis";
    }

    @RequestMapping("hystrix")
    public String hystrix(ModelMap modelMap) {
        modelMap.addAttribute("whichwebui", hystrixUrl);
        return "webuis";
    }

    @RequestMapping("kafka")
    public String kafka(ModelMap modelMap) {
        modelMap.addAttribute("whichwebui", kafkaUrl);
        return "webuis";
    }

    @RequestMapping("yarn")
    public String yarn(ModelMap modelMap) {
        modelMap.addAttribute("whichwebui", yarnUrl);
        return "webuis";
    }

    @RequestMapping("zookeeper")
    public String zookeeper(ModelMap modelMap) {
        modelMap.addAttribute("whichwebui", zookeeperUrl);
        return "webuis";
    }

}
