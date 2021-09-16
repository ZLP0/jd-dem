package com.example.jddemo.nettyrpc.v1.protocol.core;

import lombok.Data;

import java.io.Serializable;

/**
 * 通信 传输
 */
@Data
public class RpcRequest implements Serializable {

    private String className; //类名
    private String methodName; //请求目标方法
    private Object[] params;  //请求参数
    private Class<?>[] parameterTypes; //参数类型

}
