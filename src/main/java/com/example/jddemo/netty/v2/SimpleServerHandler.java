package com.example.jddemo.netty.v2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 程序员  by dell
 * time  2021-09-11
 **/

public class SimpleServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
          ByteBuf in=(ByteBuf) msg;
        byte[] req=new byte[in.readableBytes()];
        in.readBytes(req); //把数据读到byte数组中
        String body=new String(req,"UTF-8");
        System.out.println("服务器端收到消息："+body);
        //写回数据
       /* ByteBuf resp= Unpooled.copiedBuffer(UUID.randomUUID().toString().getBytes());
        ctx.writeAndFlush(resp);*/
    }
}
