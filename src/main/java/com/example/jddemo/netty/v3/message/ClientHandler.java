package com.example.jddemo.netty.v3.message;

import com.example.jddemo.netty.v3.message.protocol.MessageRecord;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 客户端 处理器
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageRecord messageRecord = (MessageRecord) msg;
        System.out.println("客户端读取数据：" + messageRecord);
    }
}
