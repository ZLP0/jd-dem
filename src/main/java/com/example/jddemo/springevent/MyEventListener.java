package com.example.jddemo.springevent;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 程序员  by dell
 * time  2021-08-18
 **/
@Component
public class MyEventListener implements ApplicationListener<MyEvent> {

    @Override
    public void onApplicationEvent(MyEvent myEvent) {
        System.out.println("监听事件");
    }
}
