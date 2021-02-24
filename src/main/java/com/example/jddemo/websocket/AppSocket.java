package com.example.jddemo.websocket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppSocket {

    @RequestMapping(value = "/index")
    public String index(){
        return "/index.html";
    }
}
