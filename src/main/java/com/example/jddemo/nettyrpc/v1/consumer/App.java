package com.example.jddemo.nettyrpc.v1.consumer;


import com.example.jddemo.nettyrpc.v1.api.IUserService;

public class App {

    public static void main(String[] args) {
       //RpcClientProxy rcp=new RpcClientProxy();
       //IUserService userService=rcp.clientProxy(IUserService.class,"127.0.0.1",8080);
        IUserService userService = ClientProxy.getInstance(IUserService.class, "127.0.0.1", 8080);
        System.out.println(userService.saveUser("zhangSang"));

    }
}
