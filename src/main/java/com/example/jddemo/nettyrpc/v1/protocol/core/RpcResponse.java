package com.example.jddemo.nettyrpc.v1.protocol.core;

import lombok.Data;

import java.io.Serializable;

/**
 * 通信 传输
 */
@Data
public class RpcResponse implements Serializable {

    private Object data;
    private String msg;
}
