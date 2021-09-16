package com.example.jddemo.nettyrpc.v1.protocol.core;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 请求头
 */
@AllArgsConstructor
@Data
public class Header {
    /**
     * 魔数
     * 自定义协议中魔数的作用:
     * 快速 识别字节流是否是程序能够处理的，能处理才进行后面的 耗时 业务操作，如果不能处理，尽快执行失败，断开连接等操作。
     */
    private short magic; //魔数 2字节
    private byte serialType; //序列化类型  1个字节
    private byte reqType; //消息类型  1个字节
    private long requestId; //请求id  8个字节
    private int length ;//消息体长度，4个字节
}
