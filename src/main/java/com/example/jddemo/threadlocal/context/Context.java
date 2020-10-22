package com.example.jddemo.threadlocal.context;

public class Context {
int a=2;

    private static final AbstractContextCore contextCore=new ContextCore();


    public static String getXID() {
        return contextCore.get("Tcc");
    }



}
