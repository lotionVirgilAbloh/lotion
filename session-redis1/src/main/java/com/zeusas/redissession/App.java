package com.zeusas.redissession;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
@RequestMapping(value = "/r1")
@EnableRedisHttpSession(redisFlushMode = RedisFlushMode.IMMEDIATE)
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }


    @Resource(name = "defaultRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Resource(name = "cacheRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate1;

    @RequestMapping(value = "/redis1")
    public String redis1() {
        redisTemplate.opsForValue().set("slzzzz", "111111");
        return "o";
    }

    @RequestMapping(value = "/redis2")
    public String redis2() {
        redisTemplate1.opsForValue().set("slzzzz", "222222");
        return "k";
    }

    @RequestMapping(value = "/redis3")
    public String redis3() {
        Object slzzzz = redisTemplate.opsForValue ().get ("slzzzz");
        return slzzzz.toString ();
    }

    @RequestMapping(value = "/redis4")
    public String redis4() {
        Object slzzzz = redisTemplate1.opsForValue ().get ("slzzzz");

        return slzzzz.toString ();
    }
    @RequestMapping(value = "/first", method = RequestMethod.GET)
    public Map<String, Object> firstResp (HttpServletRequest request){
        Map<String, Object> map = new HashMap<> ();
        request.getSession().setAttribute("request Url", request.getRequestURL());
        map.put("request Url", request.getRequestURL());
        return map;
    }

    @RequestMapping(value = "/sessions", method = RequestMethod.GET)
    public Object sessions (HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        map.put("sessionId", request.getSession().getId());
        map.put("message", request.getSession().getAttribute("map"));
        return map;
    }
}
