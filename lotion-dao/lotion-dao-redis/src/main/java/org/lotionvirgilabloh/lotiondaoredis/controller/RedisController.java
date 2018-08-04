package org.lotionvirgilabloh.lotiondaoredis.controller;


import org.lotionvirgilabloh.lotiondaoredis.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("redis")
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;


    @RequestMapping("put")
    public boolean put(String key,String v){
        RedisUtil ru =new RedisUtil();
        ru.setRedisTemplate(redisTemplate);
        boolean flag = ru.set(key,v);
        return  flag;
    }

    @RequestMapping("get")
    public String get(String key){
        RedisUtil ru =new RedisUtil();
        ru.setRedisTemplate(redisTemplate);
        Object v = ru.get(key);
        return  v.toString();
    }


}
