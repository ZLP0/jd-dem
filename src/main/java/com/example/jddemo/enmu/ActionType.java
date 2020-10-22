package com.example.jddemo.enmu;

public enum ActionType {

    BID_EN(1);
    String str="枚举";
    private int type;

    ActionType(int type) {

        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
