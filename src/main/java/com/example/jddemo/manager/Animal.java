package com.example.jddemo.manager;

import javax.annotation.PostConstruct;

public class Animal {

    protected ActionType actionType;


    public Animal(ActionType actionType) {
        this.actionType = actionType;
    }


    @PostConstruct
    public void initInstance() {
        Manager.getInstance().regist(this.actionType, this);
    }


}
