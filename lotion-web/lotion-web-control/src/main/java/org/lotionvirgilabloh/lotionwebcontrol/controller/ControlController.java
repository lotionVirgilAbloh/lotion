package org.lotionvirgilabloh.lotionwebcontrol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class ControlController {

    @RequestMapping("realtimejob")
    public String realtimejob(){
        return "realtimejob";
    }

    @RequestMapping("offlinejob")
    public String offlinejob(){
        return "offlinejob";
    }

}