package com.example.jddemo.netty.v1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


/**
 * handler IO 处理
 */
public class NormalMessageHandler extends ChannelInboundHandlerAdapter {


    //channelReadComplete方法表示消息读完了的处理，writeAndFlush方法表示写入并发送消息
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //这里的逻辑就是所有的消息读取完毕了，在统一写回到客户端。Unpooled.EMPTY_BUFFER表示空消息，addListener(ChannelFutureListener.CLOSE)表示写完后，就关闭连接
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        byte[] bytes = new byte[in.readableBytes()];
        in.readBytes(bytes);

        System.out.println("服务端读取数据：" + new String(bytes, "utf-8"));
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
