package com.example.jddemo;

import com.example.jddemo.copy.Person;
import com.example.jddemo.jackson.JacksonUtils;

/**
 * 程序员  by dell
 * time  2020-12-09
 **/

public class App {


    public static void main(String[] args) {
        Person person = new Person();
        person.setName("张三");
        person.setAge(13);
        String s = JacksonUtils.toJson(person);
        System.out.println(s);
        Person person1 = JacksonUtils.fromIgnoreJson(s, Person.class);
        System.out.println(person1);
    }


}
