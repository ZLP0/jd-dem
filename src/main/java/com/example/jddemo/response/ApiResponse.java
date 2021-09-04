package com.example.jddemo.response;

public class ApiResponse<T> extends AbstractResponse<T> {

    public static final int CODE_EXCEPTION = 0;

    public static final int CODE_SUCCESS = 1;

    public static final int CODE_WARN = -2;


    public ApiResponse() {
        init(CODE_SUCCESS);
    }

    public ApiResponse(int code, String message, T data) {
        this.setCode(code);
        this.setMessage(message);
        this.setData(data);
    }

    public void init(int code) {
        setCode(code);
    }


    public static <T> ApiResponse<T> buildSuccess(String msg) {
        return new ApiResponse<T>(CODE_SUCCESS, msg, null);
    }

    public static <T> ApiResponse<T> buildSuccess(String msg, T data) {
        return new ApiResponse<T>(CODE_SUCCESS, msg, data);
    }

    public static <T> ApiResponse<T> buildFail(String msg) {
        return new ApiResponse<T>(CODE_EXCEPTION, msg, null);
    }

    public static <T> ApiResponse<T> buildFail(String msg, T data) {
        return new ApiResponse<T>(CODE_EXCEPTION, msg, data);
    }

    public static <T> ApiResponse<T> buildWarn(String msg) {
        return new ApiResponse<T>(CODE_WARN, msg, null);
    }

    public static <T> ApiResponse<T> buildWarn(String msg, T data) {
        return new ApiResponse<T>(CODE_WARN, msg, data);
    }
}
