package org.lotionvirgilabloh.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class LoginController {

       @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }


}
