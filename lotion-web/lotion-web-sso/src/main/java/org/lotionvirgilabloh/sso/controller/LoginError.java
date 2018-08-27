package org.lotionvirgilabloh.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginError {

    @RequestMapping("/login")
    public String dashboard() {
        return "redirect:/#/";
    }

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

}
