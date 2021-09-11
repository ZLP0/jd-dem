package com.example.jddemo.netty.v2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.internal.StringUtil;

/**
 * ByteBuf 使用
 */
public class ByteBufExample {

    public static void main(String[] args) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.heapBuffer();//可自动扩容
        buf.writeBytes(new byte[]{1, 2, 3, 4}); //写入四个字节
        log(buf);
        buf.writeInt(5); //写入一个int类型，也是4个字节
        log(buf);
    }

    private static void log(ByteBuf byteBuf) {
        System.out.println(byteBuf);
        StringBuilder stringBuilder = new StringBuilder().append(" read index:").append(byteBuf.readerIndex())// 获取读索引
                .append(" write index:").append(byteBuf.writerIndex())//获取写索引
                .append(" capacity:").append(byteBuf.capacity())//获取容量
                .append(StringUtil.NEWLINE);//换行符
        //把 ByteBuf中的内容 dump到stringBuilder中
        ByteBufUtil.appendPrettyHexDump(stringBuilder, byteBuf);
        System.out.println(stringBuilder.toString());
    }
}
