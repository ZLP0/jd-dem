package com.example.jddemo.threadlocal.context;

public class Context {


    private static final AbstractContextCore contextCore=new ContextCore();


    public static String getXID() {
        return contextCore.get("Tcc");
    }



}
