package com.example.jddemo.nettyrpc.v1.consumer;

import com.example.jddemo.nettyrpc.v1.protocol.NettyClient;
import com.example.jddemo.nettyrpc.v1.protocol.constants.ReqType;
import com.example.jddemo.nettyrpc.v1.protocol.constants.RpcConstant;
import com.example.jddemo.nettyrpc.v1.protocol.constants.RpcProtocol;
import com.example.jddemo.nettyrpc.v1.protocol.constants.SerialType;
import com.example.jddemo.nettyrpc.v1.protocol.core.*;
import io.netty.channel.DefaultEventLoop;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


@Slf4j
public class RpcInovkerProxy implements InvocationHandler {
    private String host;
    private int port;

    public RpcInovkerProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("begin invoke target server");
        RpcProtocol<RpcRequest> reqProtocol=new RpcProtocol<>();
        long requestId= RequestHolder.REQUEST_ID.incrementAndGet();
        Header header=new Header(RpcConstant.MAGIC, SerialType.JSON_SERIAL.code(),
                ReqType.REQUEST.code(),requestId,0);
        reqProtocol.setHeader(header);
        RpcRequest request=new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParams(args);
        reqProtocol.setContent(request);


        NettyClient nettyClient=new NettyClient(host,port);
        RpcFuture<RpcResponse> future=new RpcFuture<>(new DefaultPromise<RpcResponse>(new DefaultEventLoop()));
        RequestHolder.REQUEST_MAP.put(requestId,future);
        nettyClient.sendRequest(reqProtocol);
        return future.getPromise().get().getData();
    }
}
