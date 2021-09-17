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
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * cglib 动态代理   代理类无需实现接口
 */
@Slf4j
public class ClientProxy implements MethodInterceptor {

    private String host;
    private int port;

    public ClientProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static <T> T getInstance(Class<T> clazz, String host, int port) {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new ClientProxy(host, port));
        return (T) enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] params, MethodProxy methodProxy) throws Throwable {
        log.info("begin invoke target server");
        RpcProtocol<RpcRequest> reqProtocol = new RpcProtocol<>();
        long requestId = RequestHolder.REQUEST_ID.incrementAndGet();
        Header header = new Header(RpcConstant.MAGIC, SerialType.JSON_SERIAL.code(), ReqType.REQUEST.code(), requestId, 0);
        reqProtocol.setHeader(header);
        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParams(params);
        reqProtocol.setContent(request);


        NettyClient nettyClient = new NettyClient(host, port);
        RpcFuture<RpcResponse> future = new RpcFuture<>(new DefaultPromise<RpcResponse>(new DefaultEventLoop()));
        RequestHolder.REQUEST_MAP.put(requestId, future);//设置   Future 存储在Map中 在RpcClientHandler中 等待服务端返回结果 并处理Future 结果
        nettyClient.sendRequest(reqProtocol);
        return future.getPromise().get().getData();
    }
}
