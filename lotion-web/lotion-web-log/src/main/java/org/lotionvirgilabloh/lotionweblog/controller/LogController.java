package org.lotionvirgilabloh.lotionweblog.controller;

import org.lotionvirgilabloh.lotionweblog.service.SseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Controller
public class LogController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SseService sseService;

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

    @RequestMapping("/exceptionsupervision")
    public String exceptionsupervision(ModelMap modelMap) {
        logger.info("LogController获取请求:/exceptionsupervision");
        modelMap.addAttribute("webStaticUrl", webStaticUrl);
        modelMap.addAttribute("webHostUrl", webHostUrl);
        return "exceptionsupervision";
    }

    @RequestMapping("/exceptionsupervision/rtjournal")
    public SseEmitter rtjournal() {
        logger.info("LogController获取请求:/exceptionsupervision/rtjournal");
        //设置SSE超时时间为30分钟
        final SseEmitter sseEmitter = new SseEmitter(1800000L);
        //回应连接已建立
        try {
            sseEmitter.send(SseEmitter.event().build());
        } catch (IOException e) {
            logger.error("SSE连接建立失败");
            sseEmitter.complete();
        }
        sseService.register(sseEmitter);
        return sseEmitter;
    }
}
