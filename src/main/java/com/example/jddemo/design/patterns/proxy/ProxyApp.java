package com.example.jddemo.design.patterns.proxy;

public class ProxyApp {

    public static void main(String[] args) {
        Hello hello = CglibProxy.getInstance(Hello.class);
        String say =hello.say("张三");
        System.out.println(say);
    }
}
