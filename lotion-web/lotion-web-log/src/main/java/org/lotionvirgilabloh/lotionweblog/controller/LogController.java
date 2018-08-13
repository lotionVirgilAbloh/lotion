package org.lotionvirgilabloh.lotionweblog.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
public class LogController {

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


}
