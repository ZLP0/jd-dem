package com.example.jddemo.manager;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ValidatorFactory {

    public static Map<String, Validator<?>> data = null;

    static final ValidatorFactory ANIMAL_FACTORY = new ValidatorFactory();

    private ValidatorFactory() {

    }

    public static ValidatorFactory getInstance() {

        return ANIMAL_FACTORY;
    }

    public void register(String key, Validator<?> validator) {
        if (StringUtils.isBlank(key) || validator == null) {
            //表明向方法返回了一个不合法或者不正确的参数
            throw new IllegalArgumentException(" key or obj can not null");
        }
        Map<String, Validator<?>> temp = null;
        if (data == null) {
            temp = new HashMap<>(1);
        } else {
            temp = new HashMap(data);
        }
        if (temp.containsKey(key)) {
            throw new IllegalArgumentException(" key 已存在");
        }
        temp.put(key, validator);
        data = temp;
    }
}
