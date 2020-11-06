package com.example.jddemo.mergelist;

import com.example.jddemo.copy.Person;

import java.util.ArrayList;
import java.util.Optional;

/**
 * @Classname App
 * @Description
 * @Author
 * @Date 2020/10/27
 * @Version 1.0
 **/
public class App {

    public static void main(String[] args) {
     ArrayList<Person> list1 = new ArrayList<>();
        Person person = new Person();
        person.setName("张三");
        list1.add(person);

        ArrayList<Person> list2 = new ArrayList<>();
        Person person2 = new Person();
        person2.setName("张三2");
        list2.add(person2);
        list1.addAll(list2);
        System.out.println(list1);


    }
}
