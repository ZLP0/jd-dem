package com.example.jddemo.redis.limit;

/**
 * 程序员  by dell
 * time  2021-02-23
 *
 * 一个注解搞定 SpringBoot 接口防刷 (接口幂等性)
 **/

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)
public @interface AccessLimit {

    int seconds();
    int maxCount();
    boolean needLogin()default true;
}
