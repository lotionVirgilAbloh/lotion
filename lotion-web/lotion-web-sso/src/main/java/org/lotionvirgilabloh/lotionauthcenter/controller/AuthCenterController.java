package org.lotionvirgilabloh.lotionauthcenter.controller;

import org.lotionVirgilAbloh.lotionbase.dto.LotionUser;
import org.lotionvirgilabloh.lotionauthcenter.service.AuthCenterManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class AuthCenterController {


    @Autowired
    AuthCenterManager authCenterManager;

    @RequestMapping(value ="login",method = RequestMethod.GET)
    public String loginPage(){
        return "loginPage";
    }
    @RequestMapping(value ="login",method = RequestMethod.POST)
    @ResponseBody
    public Boolean login(String userName,String password){
        LotionUser user =authCenterManager.getByUsername(userName);
        if(user!=null){
            if(user.getPassword().equals(password)){
                return  true;
            }
        }
        return false;
    }

    @RequestMapping(value ="logout",method = RequestMethod.POST)
    @ResponseBody
    public Boolean logout(){
        return false;
    }

    @RequestMapping("/user")
    @ResponseBody
    public Principal user(Principal principal) {
        return principal;
    }

}
