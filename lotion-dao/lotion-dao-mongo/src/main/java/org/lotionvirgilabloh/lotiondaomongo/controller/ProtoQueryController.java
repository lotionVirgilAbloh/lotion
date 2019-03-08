package org.lotionvirgilabloh.lotiondaomongo.controller;


import org.lotionvirgilabloh.lotionbase.dto.DatatablesReturn;
import org.lotionvirgilabloh.lotionbase.query.LotionQueryParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("mongoquery")
@RestController
public class ProtoQueryController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @RequestMapping("cmd")
    <T> DatatablesReturn<T> cmd(String cmd, LotionQueryParam queryParam){
        logger.info (cmd);
        return null;
    }

}
