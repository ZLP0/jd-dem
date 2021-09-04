package com.example.jddemo.manager;

public interface Validator<T> {

    public String validator(T data);

    String getModule();

}
