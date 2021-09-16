package com.example.jddemo.nettyrpc.v1.protocol.handler;

import com.example.jddemo.nettyrpc.v1.protocol.constants.RpcProtocol;
import com.example.jddemo.nettyrpc.v1.protocol.core.RequestHolder;
import com.example.jddemo.nettyrpc.v1.protocol.core.RpcFuture;
import com.example.jddemo.nettyrpc.v1.protocol.core.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户端 io 处理 读写
 */
@Slf4j
public class RpcClientHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcResponse>> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcProtocol<RpcResponse> msg) throws Exception {
        log.info("receive Rpc Server Result");
        long requestId=msg.getHeader().getRequestId();
        RpcFuture<RpcResponse> future= RequestHolder.REQUEST_MAP.remove(requestId);
        future.getPromise().setSuccess(msg.getContent()) ; //返回结果
    }
}
