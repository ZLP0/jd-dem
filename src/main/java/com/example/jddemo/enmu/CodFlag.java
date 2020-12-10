package com.example.jddemo.enmu;

public enum CodFlag {

    ONLINE_PAY(0, "否"),
    CASH_ON_DELIVRY(1, "是");

    private Integer code;
    private String text;

    CodFlag(Integer code, String text) {
        this.code = code;
        this.text = text;
    }


    public static CodFlag getItemBycode(Integer code) {
        for (CodFlag item : values()) {
            if (item.code == code) {
                return item;
            }
        }
        return null;
    }

    public static String getTextBycode(Integer code) {
        for (CodFlag item : values()) {
            if (item.code == code) {
                return item.text;
            }
        }
        return "";
    }
}
