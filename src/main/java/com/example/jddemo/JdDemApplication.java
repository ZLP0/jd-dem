package com.example.jddemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.jddemo.sharding")
public class JdDemApplication {
     public static void main(String[] args) throws Exception {
        SpringApplication.run(JdDemApplication.class, args);
    }
}
