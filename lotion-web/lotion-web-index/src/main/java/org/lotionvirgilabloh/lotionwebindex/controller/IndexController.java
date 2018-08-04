package org.lotionvirgilabloh.lotionwebindex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping("index")
    public String index(){
        return "index";
    }

    @RequestMapping("indexframe")
    public String indexframe() {
        return "indexframe";
    }
}
