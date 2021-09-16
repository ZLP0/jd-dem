package com.example.jddemo.nettyrpc.v1.protocol.handler;

import com.example.jddemo.nettyrpc.v1.protocol.constants.ReqType;
import com.example.jddemo.nettyrpc.v1.protocol.constants.RpcProtocol;
import com.example.jddemo.nettyrpc.v1.protocol.core.Header;
import com.example.jddemo.nettyrpc.v1.protocol.core.RpcRequest;
import com.example.jddemo.nettyrpc.v1.protocol.core.RpcResponse;
import com.example.jddemo.nettyrpc.v1.protocol.spring.SpringBeanManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 处理 io 读写
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcRequest>> {
    @Override

    protected void channelRead0(ChannelHandlerContext ctx, RpcProtocol<RpcRequest> msg) throws Exception {
        RpcProtocol<RpcResponse> protocol = new RpcProtocol<>();
        Header header = msg.getHeader();
        header.setReqType(ReqType.RESPONSE.code());
        Object result = invoke(msg.getContent());//调用 实现类 获取结果
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setData(result);
        rpcResponse.setMsg("成功");

        protocol.setHeader(header);
        protocol.setContent(rpcResponse);
        ctx.writeAndFlush(protocol);//写出 数据
    }

    /**
     * 利用 反射调用
     *
     * @param rpcRequest
     * @return
     */
    private Object invoke(RpcRequest rpcRequest) {
        try {
            Class<?> clazz = Class.forName(rpcRequest.getClassName());
            Object bean = SpringBeanManager.getBean(clazz);
            Method declaredMethod = clazz.getDeclaredMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
            return declaredMethod.invoke(bean, rpcRequest.getParams());
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
