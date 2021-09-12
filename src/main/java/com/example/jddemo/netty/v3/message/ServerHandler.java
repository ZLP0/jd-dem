package com.example.jddemo.netty.v3.message;

import com.example.jddemo.netty.v3.message.opcode.OpCode;
import com.example.jddemo.netty.v3.message.protocol.MessageRecord;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 程序员  by dell
 * time  2021-09-12
 **/

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageRecord messageRecord = (MessageRecord) msg;
        System.out.println("server read message:" + messageRecord);

        messageRecord.getHeader().setReqType(OpCode.RES.code());//服务端返回的消息类型
        messageRecord.setBody("server Response Message");
        ctx.writeAndFlush(msg);//服务端返回消息
    }
}
