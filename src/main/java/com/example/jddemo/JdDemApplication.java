package com.example.jddemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JdDemApplication {
     public static void main(String[] args) throws Exception {
        SpringApplication.run(JdDemApplication.class, args);
         System.out.println("测试git版本回退");
    }
}
