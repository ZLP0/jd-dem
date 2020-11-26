package com.example.jddemo.response;

public class ResponseEntity <T> {

    public static  final   int  CODE_EXCEPTION=0;

    public static final  int  CODE_NORMAL=1;

    public static  final int  CODE_WARN=2;


    private String message;

    private T Data;

    private int code;

    public ResponseEntity( ) {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
