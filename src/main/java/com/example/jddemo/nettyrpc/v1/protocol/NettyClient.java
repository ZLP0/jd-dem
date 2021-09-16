package com.example.jddemo.nettyrpc.v1.protocol;

import com.example.jddemo.netty.v3.message.ClientHandler;
import com.example.jddemo.netty.v3.message.codec.MessageRecordDecode;
import com.example.jddemo.netty.v3.message.codec.MessageRecordEncode;
import com.example.jddemo.nettyrpc.v1.protocol.constants.RpcProtocol;
import com.example.jddemo.nettyrpc.v1.protocol.core.RpcRequest;
import com.example.jddemo.nettyrpc.v1.protocol.encode.RpcDecoder;
import com.example.jddemo.nettyrpc.v1.protocol.encode.RpcEncoder;
import com.example.jddemo.nettyrpc.v1.protocol.handler.RpcClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * netty 客户端
 */
@Slf4j
public class NettyClient {

    private final Bootstrap bootstrap;
    private final EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    private String serviceAddress;
    private int servicePort;

    public NettyClient(String serviceAddress, int servicePort) {
        log.info("begin init Netty Client,{},{}", serviceAddress, servicePort);
        bootstrap = new Bootstrap();

        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        log.info("begin RpcClientInitializer");
                        ch.pipeline().addLast(
                                new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,
                                        12,
                                        4,
                                        0, 0))
                                .addLast(new LoggingHandler())
                                .addLast(new RpcEncoder())
                                .addLast(new RpcDecoder())
                                .addLast(new RpcClientHandler());
                    }
                });

        this.serviceAddress = serviceAddress;
        this.servicePort = servicePort;
    }

    /**
     * 发送 请求
     *
     * @param protocol
     * @throws InterruptedException
     */
    public void sendRequest(RpcProtocol<RpcRequest> protocol) throws InterruptedException {
        ChannelFuture future = bootstrap.connect(serviceAddress, servicePort).sync();
        future.addListener(listener -> {
            if (future.isSuccess()) {
                log.info("connect rpc server {} success.", this.serviceAddress);
            } else {
                log.error("connect rpc server {} failed. ", this.serviceAddress);
                future.cause().printStackTrace();//失败 打印出 栈信息
                eventLoopGroup.shutdownGracefully();
            }
        });
        log.info("begin transfer data");
        future.channel().writeAndFlush(protocol);

    }
}
