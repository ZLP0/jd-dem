package com.example.jddemo.manager;

import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class Cat extends Animal {

    public Cat()
    {
        super(ActionType.SUB);
    }
}
