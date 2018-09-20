package org.lotionvirgilabloh.lotionwebindex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginError {

    @RequestMapping("/lotion/login")
    public String dashboard() {
        return "redirect:/#/";
    }

}
