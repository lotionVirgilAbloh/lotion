package org.lotionvirgilabloh.lotionweblog.controller;

import org.lotionvirgilabloh.lotionbase.dto.DatatablesRetrieve;
import org.lotionvirgilabloh.lotionbase.dto.DatatablesReturn;
import org.lotionvirgilabloh.lotionbase.dto.FormattedException;
import org.lotionvirgilabloh.lotionbase.util.DatatablesRetrieveConverter;
import org.lotionvirgilabloh.lotionweblog.service.ExceptionDaoService;
import org.lotionvirgilabloh.lotionweblog.service.SseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LogController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SseService sseService;

    @Autowired
    private ExceptionDaoService exceptionDaoService;

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
        } catch (Exception e) {
            logger.error("SSE连接建立失败");
            sseEmitter.complete();
        }
        sseService.register(sseEmitter);
        return sseEmitter;
    }

    @RequestMapping("/exceptionsupervision/datatables")
    public @ResponseBody
    DatatablesReturn<FormattedException> datatables(HttpServletRequest request) {
        logger.info("ExceptionDaoController获取请求:/exceptionsupervision/datatables");
        DatatablesRetrieve datatablesRetrieve = null;
        try {
            datatablesRetrieve = DatatablesRetrieveConverter.convert(request);
        } catch (Exception e) {
            logger.error("DatatablesRetrieve转换失败", e);
        }
        if (datatablesRetrieve != null) {
            logger.info(datatablesRetrieve.toString());
        }

        DatatablesReturn<FormattedException> d = exceptionDaoService.findAllPage(datatablesRetrieve);
        return d;
    }
}
