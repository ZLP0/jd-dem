package com.example.jddemo.nettyrpc.v1.consumer;

import java.lang.reflect.Proxy;


public class RpcClientProxy {

    public <T> T clientProxy(final Class<T> interfaceCls,final String host,int port){
        return (T) Proxy.newProxyInstance(interfaceCls.getClassLoader(),
                new Class<?>[]{interfaceCls},new RpcInovkerProxy(host,port));
    }
}
