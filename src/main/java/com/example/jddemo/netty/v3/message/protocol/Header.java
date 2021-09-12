package com.example.jddemo.netty.v3.message.protocol;

import lombok.Data;

/**
 * 程序员  by dell
 * time  2021-09-12
 **/

@Data
public class Header {
    private long sessionId;  //会话id，8个字节
    private byte reqType;  //消息类型， 1个字节
    private int length; //消息体的长度  4个字节
}
