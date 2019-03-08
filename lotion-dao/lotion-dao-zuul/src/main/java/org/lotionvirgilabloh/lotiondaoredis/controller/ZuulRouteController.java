package org.lotionvirgilabloh.lotiondaoredis.controller;

import org.lotionvirgilabloh.lotionbase.dto.BaseMapDTO;
import org.lotionvirgilabloh.lotionbase.query.LotionQueryParam;
import org.lotionvirgilabloh.lotiondaoredis.service.MongoFeignClient;
import org.lotionvirgilabloh.lotiondaoredis.service.MySqlFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping("mongoDemo")
    public BaseMapDTO mongoDemo(){
        LotionQueryParam queryParam  =new LotionQueryParam();
        mongoFeignClient.cmd("cmdTest",queryParam);
        return null;
    }

    @RequestMapping("mysqlDemo")

    public BaseMapDTO mysqlDemo(){
        LotionQueryParam queryParam =new LotionQueryParam();
        mySqlFeignClient.excute("sqll",queryParam);
        return  null;
    }



}
