package com.example.jddemo.threadlocal.context;

public class Context {

    String  str="单个文件回滚";
    private static final AbstractContextCore contextCore=new ContextCore();

    public static String getXID() {
        return contextCore.get("Tcc");
    }

}
