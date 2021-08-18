package com.example.jddemo.springevent;

import org.springframework.context.ApplicationEvent;

/**
 * 程序员  by dell
 * time  2021-08-18
 **/

public class MyEvent  extends ApplicationEvent {

    private String data;

    public MyEvent(Object source) {
        super(source);
    }

    public MyEvent(Object source, String data) {
        super(source);
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
