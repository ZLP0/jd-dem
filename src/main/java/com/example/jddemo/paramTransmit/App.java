package com.example.jddemo.paramTransmit;

import java.util.HashMap;
import java.util.Map;

public class App {


    public static void main(String[] args) {
        HashMap map = new HashMap();
        new App().test(map);
        System.out.println(map);


    }

    public void test(Map map) {

        map.put("a", "1");

        test2(map);


    }

    public void test2(Map map) {

        map.put("b", "2");
    }
}
