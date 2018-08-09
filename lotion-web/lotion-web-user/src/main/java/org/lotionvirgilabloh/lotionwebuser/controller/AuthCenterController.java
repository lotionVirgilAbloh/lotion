package org.lotionvirgilabloh.lotionwebuser.controller;

import org.lotionvirgilabloh.lotionwebuser.service.AuthCenterManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("auth")
@RestController
public class AuthCenterController {


    public AuthCenterManager authCenterManager;

    @RequestMapping("login")
    public Boolean login(String userName,String password){
        User user =authCenterManager.getByUsername(userName);
        if(user.getPassword().equals(password)){
            return  true;
        }
        return false;
    }

}
