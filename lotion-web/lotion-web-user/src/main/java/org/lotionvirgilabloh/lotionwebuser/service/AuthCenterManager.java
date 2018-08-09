package org.lotionvirgilabloh.lotionwebuser.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "lotion-dao-mysql")
@RequestMapping("user")
public interface AuthCenterManager {

    @GetMapping(value = "getByUsername")
    User getByUsername(@RequestParam("username") String username);
}
