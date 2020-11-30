package com.example.jddemo.webcoket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class App {

    @RequestMapping(value = "/index")
    public String index(){
        return "/index.html";
    }
}
