package org.lotionvirgilabloh.lotionwebapplication;

import org.lotionvirgilabloh.lotionbase.http.LotionResponse;
import org.lotionvirgilabloh.lotionbase.query.LotionQueryParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Created by umiderrick on 2019/3/8.
 */
@FeignClient(name ="lotion-dao-zuul",configuration = FeignConfig.class)
@RequestMapping("route")
public interface HelloZuulService {

    @RequestMapping(value = "health",method = RequestMethod.GET)
    String getHealth();

    @PostMapping("mongoDemo")
    LotionResponse mongoDemo(@RequestParam("cmd") String cmd , @RequestParam("param") LotionQueryParam param);

    @PostMapping("mysqlexcute")
    LotionResponse mysqlDemo(@RequestParam("sql")String sql ,@RequestParam("param") LotionQueryParam param);

}
