package org.lotionvirgilabloh.lotionmessagebus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EventController {
    private ApplicationContext context;

    @Autowired
    public EventController(ApplicationContext context) {
        this.context = context;
    }

    @RequestMapping(value = "/publishbusevent", method = RequestMethod.GET)
    public String publishEvent(@RequestParam String message, @RequestParam String originService, @RequestParam(value = "destination", required = false, defaultValue = "**") String destination) {

//        final LotionTestEvent lotionTestEvent = new LotionTestEvent(this, originService, destination, message);

//        context.publishEvent(lotionTestEvent);

        return "event published";
    }
}
