package org.lotionvirgilabloh.sso.service;


import org.lotionvirgilabloh.lotionbase.auth.LotionPermission;
import org.lotionvirgilabloh.lotionbase.auth.LotionRole;
import org.lotionvirgilabloh.lotionbase.auth.LotionUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "lotion-dao-mysql")
@RequestMapping("user")
public interface AuthCenterManager {

    @GetMapping(value = "getByUsername")
    LotionUser getByUsername(@RequestParam("username") String username);

    @GetMapping(value = "getAllPerm")
    List<LotionPermission>  getAllPerm();

    @GetMapping(value = "getAllRole")
     List<LotionRole> getAllRole();

}
