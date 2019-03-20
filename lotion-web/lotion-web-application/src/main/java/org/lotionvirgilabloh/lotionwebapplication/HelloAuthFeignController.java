package org.lotionvirgilabloh.lotionwebapplication;

import org.lotionvirgilabloh.lotionbase.http.LotionResponse;
import org.lotionvirgilabloh.lotionbase.query.LotionQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by umiderrick on 2019/3/8.
 */
@Import(FeignConfig.class)
@RestController
public class HelloAuthFeignController {

    @Autowired
    private HelloZuulService helloZuulService;



    @RequestMapping("test")
    String vv(){
        return helloZuulService.getHealth();
    }

    @RequestMapping("test2")
    LotionResponse vv2(){
        LotionQueryParam lqp =new LotionQueryParam();
        lqp.setGetAndForget(true);
        lqp.setGetRedis(true);
        lqp.setStoreMin(true);
        lqp.setParaGet(true);
        lqp.setStoreRedis(true);
        return helloZuulService.mongoDemo("find().skip(10).limit(10)",lqp);
    }

    @RequestMapping("test3")
    LotionResponse vv3(){
        LotionQueryParam lqp =new LotionQueryParam();
        return helloZuulService.mysqlDemo("select * from roles",lqp);
    }

}
