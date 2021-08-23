package com.example.jddemo.manager;

import org.springframework.stereotype.Component;

@Component
public class DogValidator extends AbstractAnimalValidator {


    public DogValidator() {
        super(ActionType.DOG);
    }


    @Override
    public String validator(String data) {
        System.out.println("我是小汪汪");
        return "我是小汪汪";
    }

    @Override
    public String getModule() {
        return ValidatorModule.ANIMAL + ":" + ActionType.DOG.getCode();
    }
}
