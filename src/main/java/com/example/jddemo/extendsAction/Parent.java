package com.example.jddemo.extendsAction;

public class Parent {

    public  void  init(){
        System.out.println("父类执行init");
        this.doAction();
    }

    public void doAction(){

        System.out.println("父类执行行为");
    }
}
