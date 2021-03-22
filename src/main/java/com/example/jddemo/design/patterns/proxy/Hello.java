package com.example.jddemo.design.patterns.proxy;

public class Hello extends CglibProxy {

    public String say(String  name){

        return "你好 我是："+name;
    }
}
