package org.lotionvirgilabloh.lotionwebindex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginError {

    @RequestMapping("/login")
    public String dashboard() {
        return "redirect:/#/";
    }

}
