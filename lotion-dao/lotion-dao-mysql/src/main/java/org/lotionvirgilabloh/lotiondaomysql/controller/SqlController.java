package org.lotionvirgilabloh.lotiondaomysql.controller;

import org.lotionvirgilabloh.lotionbase.dto.DatatablesReturn;
import org.lotionvirgilabloh.lotionbase.query.LotionQueryParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("sql")
@RestController
public class SqlController {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @RequestMapping("/excute")
    <T> DatatablesReturn<T> excute(String sql, LotionQueryParam queryParam){
        logger.info (sql);
        return  new DatatablesReturn<> ();
    }

}
