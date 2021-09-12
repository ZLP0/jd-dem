package com.example.jddemo.netty.v3.message;

import com.example.jddemo.netty.v3.message.codec.MessageRecordDecode;
import com.example.jddemo.netty.v3.message.codec.MessageRecordEncode;
import com.example.jddemo.netty.v3.message.opcode.OpCode;
import com.example.jddemo.netty.v3.message.protocol.Header;
import com.example.jddemo.netty.v3.message.protocol.MessageRecord;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.net.InetSocketAddress;

/**
 * 客户端
 */
public class ProcotolClient {

    public static void main(String[] args) {
        EventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new LengthFieldBasedFrameDecoder(1024 * 1024, 9, 4, 0, 0))
                                .addLast(new MessageRecordEncode())
                                .addLast(new MessageRecordDecode())
                                .addLast(new ClientHandler());
                    }
                });
        try {
            ChannelFuture future = bootstrap.connect(new InetSocketAddress("localhost", 8080)).sync();
            Channel channel = future.channel();
            for (int i = 0; i < 100; i++) {
                MessageRecord messageRecord = new MessageRecord();
                Header header = new Header();
                header.setSessionId(100001);
                header.setReqType(OpCode.REQ.code());
                messageRecord.setHeader(header);
                messageRecord.setBody("我是请求数据" + i);
                channel.writeAndFlush(messageRecord);
            }
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }
    }
}
