package com.example.jddemo.manager;

import java.util.HashMap;
import java.util.Map;

public class AnimalFactory {

    public static Map<String, Animal<?>> data = new HashMap();

    static final AnimalFactory ANIMAL_FACTORY = new AnimalFactory();

    private AnimalFactory() {

    }


    public static AnimalFactory getInstance() {

        return ANIMAL_FACTORY;
    }

    public void regist(ActionType type, Animal<?> obj) {

        if (type == null || obj == null) {
            //表明向方法返回了一个不合法或者不正确的参数
            throw new IllegalArgumentException(" type or obj can not null");
        }
        Map<String, Animal<?>> temp = new HashMap();
        if (data == null) {
            temp = new HashMap<>(8);
        } else {
            temp = new HashMap(data);
        }
        if (temp.containsKey(String.valueOf(type.getCode()))) {
            throw new IllegalArgumentException(" key 已存在");
        }
        temp.put(String.valueOf(type.getCode()), obj);
        data = temp;
    }
}
