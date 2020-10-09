package com.example.jddemo.manager;

import java.util.HashMap;

public class Manager {

    public static HashMap map = new HashMap();

    static final Manager manager = new Manager();

    private Manager() {

    }


    public static Manager getInstance() {

        return manager;
    }

    public  void regist(ActionType type, Animal obj) {

        map.put(type.getCode(), obj);

    }
}
