package com.example.jddemo.java8lambda;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        Stream<Integer> integerStream = list.stream().filter(a -> a > 1).map(a -> a);



        List<String> list2 = Arrays.asList("abc","abcd","abcde","1");

        //map管道
        List<String> collect2 = list2.stream().map(a -> a + "#").collect(Collectors.toList());
        System.out.println(collect2);

        //计算 每个字符串长度
        List<Integer> collect1 = list2.stream().map(String::length).collect(Collectors.toList());
        System.out.println(collect1);

    }
}
