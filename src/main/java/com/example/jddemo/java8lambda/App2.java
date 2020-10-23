package com.example.jddemo.java8lambda;

import java.util.HashMap;

/**
 * @Classname App2
 * @Description
 * @Author
 * @Date 2020/10/23
 * @Version 1.0
 **/
public class App2 {

    static HashMap<String,String> map =new HashMap();
    static {
        map.put("101","阿里");
        map.put("102","小米");
        map.put("103","腾讯");
    }

    public static void main(String[] args) {
        map.forEach((k,v)->{
            System.out.println("key:"+k+"|"+"val:"+v);
        });
    }
}
