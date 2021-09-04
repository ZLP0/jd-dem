package com.example.jddemo.manager;

public enum ActionType   {

    DOG(1),
    CAT(2);

    private int code;
    ActionType(int code)
    {
        this.code=code;
    }

    public int getCode() {
        return code;
    }
}
