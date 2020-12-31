package com.example.jddemo.manager;

import org.springframework.stereotype.Component;

@Component
public class Dog extends AbstractAnimal {



    public Dog() {
        super(ActionType.ADD);
    }

    @Override
    public String getModule() {
        return AnimalModule.MODULE_DOG;
    }
}
