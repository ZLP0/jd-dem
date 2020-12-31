package com.example.jddemo.manager;

import javax.annotation.PostConstruct;

public abstract class AbstractAnimal implements Animal<String>{

    protected ActionType actionType;


    public AbstractAnimal(ActionType actionType) {
        this.actionType = actionType;
    }


    @PostConstruct
    public void initInstance() {
        AnimalFactory.getInstance().regist(this.actionType, this);
    }


}
