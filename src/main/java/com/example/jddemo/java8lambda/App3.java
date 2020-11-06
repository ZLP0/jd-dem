package com.example.jddemo.java8lambda;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname App3
 * @Description
 * @Author
 * @Date 2020/11/5
 * @Version 1.0
 **/
public class App3 {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> collect = list.stream().filter(i -> i > 3).collect(Collectors.toList());
         System.out.println(collect);
    }
}
