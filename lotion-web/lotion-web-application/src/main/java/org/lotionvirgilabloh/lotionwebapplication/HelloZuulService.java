package org.lotionvirgilabloh.lotionwebapplication;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by umiderrick on 2019/3/8.
 */
@FeignClient(name ="lotion-dao-zuul"/*,configuration = FeignConfig.class*/)
public interface HelloZuulService {

    @RequestMapping(value = "route/health",method = RequestMethod.GET)
    String getHealth();

}
