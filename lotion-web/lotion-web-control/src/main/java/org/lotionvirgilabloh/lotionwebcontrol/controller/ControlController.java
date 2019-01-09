package org.lotionvirgilabloh.lotionwebcontrol.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('root')")
    @RequestMapping("realtimejob")
    public String realtimejob(ModelMap modelMap){
        try {
            throw new Exception("测试");
        } catch (Exception e) {
            logger.error("测试异常",e);
        }
        logger.info("ControlController获取请求:/realtimejob");
        modelMap.addAttribute("webStaticUrl", webStaticUrl);
        modelMap.addAttribute("webHostUrl", webHostUrl);
        return "realtimejob";
    }

    @PreAuthorize("hasAuthority('adm')")
    @RequestMapping("offlinejob")
    public String offlinejob(ModelMap modelMap){
        logger.info("ControlController获取请求:/offlinejob");
        modelMap.addAttribute("webStaticUrl", webStaticUrl);
        modelMap.addAttribute("webHostUrl", webHostUrl);
        return "offlinejob";
    }

}