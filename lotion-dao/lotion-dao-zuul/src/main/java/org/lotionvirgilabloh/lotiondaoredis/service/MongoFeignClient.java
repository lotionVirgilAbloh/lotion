package org.lotionvirgilabloh.lotiondaoredis.service;

import org.lotionvirgilabloh.lotionbase.dto.DatatablesReturn;
import org.lotionvirgilabloh.lotionbase.query.LotionQueryParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by umiderrick on 2019/3/7.
 */
@FeignClient(name="lotion-dao-mongo")
public interface MongoFeignClient {

    @RequestMapping("/mongoquery/cmd")
    <T> DatatablesReturn<T> cmd(@RequestParam("cmd") String cmd, @RequestBody LotionQueryParam queryParam);


}
