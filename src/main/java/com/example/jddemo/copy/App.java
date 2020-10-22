package com.example.jddemo.copy;

import org.springframework.beans.BeanUtils;

public class App {

   public static void main(String[] args) {

      Person person1= new Person();
      Person person2 = new Person();
      BeanUtils.copyProperties(person1,person2);







   }
}
