package com.example.jddemo.manager;

import org.springframework.stereotype.Component;

@Component
public class Dog extends Animal {



    public Dog() {
        super(ActionType.ADD);
    }

}
