package com.example.jddemo.manager;

public enum ActionType   {

    ADD(1),
    SUB(2);

    private int code;
    ActionType(int code)
    {
        this.code=code;
    }

    public int getCode() {
        return code;
    }
}
