package com.example.jddemo.netty.v3.message.protocol;

import lombok.Data;

/**
 * 程序员  by dell
 * time  2021-09-12
 **/

@Data
public class MessageRecord {
    private Header header;
    private Object body;
}
