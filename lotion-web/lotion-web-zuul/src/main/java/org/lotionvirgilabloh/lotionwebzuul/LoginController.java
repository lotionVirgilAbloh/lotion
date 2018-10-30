package org.lotionvirgilabloh.lotionwebzuul;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Controller
public class LoginController {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/user")
    public @ResponseBody
    Principal user(Principal user) {
        return user;
    }


    @PreAuthorize("hasAuthority('adm')")
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @PreAuthorize("hasAuthority('adm')")
    @RequestMapping("/#/")
    public String indexPostFix() {
        return "/";
    }

    @PreAuthorize("hasAuthority('adm')")
    @RequestMapping(value = "/testadmin")
    public @ResponseBody
    String testadmin() {
        return "admin ok";
    }

    @PreAuthorize("hasAuthority('root')")
    @RequestMapping(value = "/testroot")
    public @ResponseBody
    String testroot() {
        return "root ok";
    }

    @RequestMapping("/login")
    public String login() {
        return "/";
    }
}
