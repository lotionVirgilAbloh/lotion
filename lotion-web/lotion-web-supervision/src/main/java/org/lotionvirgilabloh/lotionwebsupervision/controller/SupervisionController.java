package org.lotionvirgilabloh.lotionwebsupervision.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class SupervisionController {

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

    @RequestMapping("kafkastreams")
    public String kafkastreams(ModelMap modelMap) {
        modelMap.addAttribute("webStaticUrl", webStaticUrl);
        modelMap.addAttribute("webHostUrl", webHostUrl);
        return "kafkastreams";
    }

    @RequestMapping("samza")
    public String samza(ModelMap modelMap) {
        modelMap.addAttribute("webStaticUrl", webStaticUrl);
        modelMap.addAttribute("webHostUrl", webHostUrl);
        return "samza";
    }

    @RequestMapping("spark")
    public String spark(ModelMap modelMap) {
        modelMap.addAttribute("webStaticUrl", webStaticUrl);
        modelMap.addAttribute("webHostUrl", webHostUrl);
        return "spark";
    }

    @RequestMapping("sparkstreaming")
    public String sparkstreaming(ModelMap modelMap) {
        modelMap.addAttribute("webStaticUrl", webStaticUrl);
        modelMap.addAttribute("webHostUrl", webHostUrl);
        return "sparkstreaming";
    }

    @RequestMapping("mapreduce")
    public String mapreduce(ModelMap modelMap) {
        modelMap.addAttribute("webStaticUrl", webStaticUrl);
        modelMap.addAttribute("webHostUrl", webHostUrl);
        return "mapreduce";
    }
}
