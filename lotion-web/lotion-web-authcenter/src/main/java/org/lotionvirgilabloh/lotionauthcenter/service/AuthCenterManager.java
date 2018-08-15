package org.lotionvirgilabloh.lotionauthcenter.service;


import org.lotionVirgilAbloh.lotionbase.dto.LotionUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "lotion-dao-mysql")
@RequestMapping("user")
public interface AuthCenterManager {

    @GetMapping(value = "getByUsername")
    LotionUser getByUsername(@RequestParam("username") String username);
}
