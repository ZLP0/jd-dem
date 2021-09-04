package com.example.jddemo.manager;

import org.springframework.stereotype.Component;

@Component
public class CatValidator extends AbstractAnimalValidator {

    public CatValidator() {
        super(ActionType.CAT);
    }

    @Override
    public String validator(String data) {
        System.out.println("我是小猫咪");
        return "我是小猫咪";
    }

    @Override
    public String getModule() {
        return ValidatorModule.ANIMAL +":"+ ActionType.CAT.getCode();
    }
}
