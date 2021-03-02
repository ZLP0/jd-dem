package com.example.jddemo.response;

public class CommonResponse<T> extends AbstractResponse<T>{

    public static  final   int  CODE_EXCEPTION=0;

    public static final  int  CODE_NORMAL=1;

    public static  final int  CODE_WARN=-2;



    public CommonResponse( ) {
        init(CODE_NORMAL);
    }
    public void init(int code){
       setCode(code);
    }


}
