package org.lotionvirgilabloh.lotionweblog.controller;

import org.lotionvirgilabloh.lotionbase.dto.DatatablesRetrieve;
import org.lotionvirgilabloh.lotionbase.dto.DatatablesReturn;
import org.lotionvirgilabloh.lotionbase.dto.FormattedException;
import org.lotionvirgilabloh.lotionbase.util.DatatablesRetrieveConverter;
import org.lotionvirgilabloh.lotionweblog.service.ExceptionDaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ExceptionDaoController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExceptionDaoService exceptionDaoService;

    @RequestMapping("/exceptionsupervision/datatables")
    public DatatablesReturn<FormattedException> datatables(HttpServletRequest request) {
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

        return exceptionDaoService.findAllPage(datatablesRetrieve.getStart(), datatablesRetrieve.getLength());
    }

}
