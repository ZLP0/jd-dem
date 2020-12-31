package com.example.jddemo.manager;

import org.springframework.stereotype.Component;

@Component
public class Cat extends AbstractAnimal {

    public Cat()
    {
        super(ActionType.SUB);
    }

    @Override
    public String getModule() {
        return null;
    }
}
