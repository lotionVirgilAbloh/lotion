package org.lotionvirgilabloh.lotiondaoredis.service;

import org.lotionvirgilabloh.lotionbase.dto.DatatablesReturn;
import org.lotionvirgilabloh.lotionbase.query.LotionQueryParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by umiderrick on 2019/3/7.
 */
@FeignClient(name="lotion-dao-mysql")
public interface MySqlFeignClient {

    @RequestMapping("sql/excute")
    List excute(@RequestParam("sql") String sql, @RequestBody LotionQueryParam queryParam);



}
