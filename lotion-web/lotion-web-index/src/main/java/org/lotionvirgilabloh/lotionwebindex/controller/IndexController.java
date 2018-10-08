package org.lotionvirgilabloh.lotionwebindex.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

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

    @RequestMapping({"/", "/index"})
    public String index(ModelMap modelMap) {
        modelMap.addAttribute("webStaticUrl", webStaticUrl);
        modelMap.addAttribute("webHostUrl", webHostUrl);
        return "index";
    }

    @RequestMapping("/indexframe")
    public String indexframe(ModelMap modelMap) {
        modelMap.addAttribute("webStaticUrl", webStaticUrl);
        modelMap.addAttribute("webHostUrl", webHostUrl);
        return "indexframe";
    }

    @RequestMapping(value = "/getWebStaticUrl", method = RequestMethod.POST)
    public @ResponseBody String requestWebStaticUrl() {
        return webStaticUrl;
    }

    @RequestMapping(value = "/getWebHostUrl", method = RequestMethod.POST)
    public @ResponseBody String requestWebHostUrl() {
        return webHostUrl;
    }

    @PreAuthorize("hasAuthority('adm')")
    @RequestMapping(value = "/testadmin")
    public @ResponseBody String testadmin() {
        return "admin ok";
    }

    @PreAuthorize("hasAuthority('root')")
    @RequestMapping(value = "/testroot")
//    @PreAuthorize("hasRole('root')")
    public @ResponseBody String testroot() {
        return "root ok";
    }
}
