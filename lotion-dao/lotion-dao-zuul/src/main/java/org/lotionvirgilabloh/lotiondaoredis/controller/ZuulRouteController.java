package org.lotionvirgilabloh.lotiondaoredis.controller;

import org.lotionvirgilabloh.lotionbase.dto.BaseMapDTO;
import org.lotionvirgilabloh.lotionbase.http.LotionResponse;
import org.lotionvirgilabloh.lotionbase.query.LotionQueryParam;
import org.lotionvirgilabloh.lotiondaoredis.service.MongoFeignClient;
import org.lotionvirgilabloh.lotiondaoredis.service.MySqlFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by umiderrick on 2019/3/7.
 */
@RequestMapping("route")
@RestController
public class ZuulRouteController {

    @Autowired
    private MongoFeignClient mongoFeignClient;

    @Autowired
    private MySqlFeignClient mySqlFeignClient;

    @RequestMapping("health")
    public String testhealth(){
        return "I m health Zuul";
    }

    @PostMapping("mongoDemo")
    public LotionResponse mongoDemo(String cmd ,LotionQueryParam param){
        try {
            mongoFeignClient.cmd(cmd,param);
        return LotionResponse.OK;
        }catch (Exception e){
            return LotionResponse.FAILURE;
        }
    }

    @PostMapping("mysqlDemo")
    public LotionResponse mysqlDemo(String sql ,LotionQueryParam param){
        try {
            mySqlFeignClient.excute(sql, param);
            return LotionResponse.OK;
        }catch (Exception e){
            return LotionResponse.FAILURE;
        }
    }



}
