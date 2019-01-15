package org.lotionvirgilabloh.lotionwebzuul.core.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @RequestMapping("/")
    public String index(){
        return "API-GateWay";
    }
}
