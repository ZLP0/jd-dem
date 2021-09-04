package com.example.jddemo.springevent;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 程序员  by dell
 * time  2021-08-18
 **/
@RestController
public class EventAppController {

    @Resource
    ApplicationEventPublisher  publisher;

    @RequestMapping(value = "pubEvent")
    public String  pubEvent(){
        publisher.publishEvent(new MyEvent(this,"张三"));
        System.out.println(11111);
        return "success";
    }
}
