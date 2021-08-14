package com.example.jddemo.java8lambda;

/**
 * @Classname Company
 * @Description
 * @Author
 * @Date 2020/10/23
 * @Version 1.0
 **/
public class Company {

    private  int  code;
    private String name;

    public Company() {
    }

    public Company(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
