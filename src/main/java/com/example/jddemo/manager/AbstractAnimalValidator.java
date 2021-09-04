package com.example.jddemo.manager;

import javax.annotation.PostConstruct;

public abstract class AbstractAnimalValidator implements Validator<String> {

    protected ActionType actionType;


    public AbstractAnimalValidator(ActionType actionType) {
        this.actionType = actionType;
    }


    @PostConstruct
    public void initInstance() {
        String key = getModule();
        ValidatorFactory.getInstance().register(key, this);
    }


}
