package org.lotionvirgilabloh.lotionwebcontrol.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ControlController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

    @RequestMapping("realtimejob")
    public String realtimejob(ModelMap modelMap){
        try{
            throw(new Exception());
        } catch (Exception e) {
            logger.error("abc", e);
        }
        modelMap.addAttribute("webStaticUrl", webStaticUrl);
        modelMap.addAttribute("webHostUrl", webHostUrl);
        return "realtimejob";
    }

    @RequestMapping("offlinejob")
    public String offlinejob(ModelMap modelMap){
        modelMap.addAttribute("webStaticUrl", webStaticUrl);
        modelMap.addAttribute("webHostUrl", webHostUrl);
        return "offlinejob";
    }

}