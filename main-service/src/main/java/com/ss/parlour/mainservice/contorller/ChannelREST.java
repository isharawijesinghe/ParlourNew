package com.ss.parlour.mainservice.contorller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(path = "/main")
public class ChannelREST {

    @RequestMapping(value = "/version", method = RequestMethod.GET)
    public String hello() {
        return "Hello parlour:0.0.1 " + System.currentTimeMillis();
    }
}
