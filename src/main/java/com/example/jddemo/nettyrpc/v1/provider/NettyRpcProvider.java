package com.example.jddemo.nettyrpc.v1.provider;

import com.example.jddemo.nettyrpc.v1.protocol.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 程序员  by dell
 * time  2021-09-16
 **/
@ComponentScan(basePackages = {"com.example.jddemo.nettyrpc.v1.provider.service","com.example.jddemo.nettyrpc.v1.protocol.spring"})
@SpringBootApplication
public class NettyRpcProvider {

    public static void main(String[] args) {
        SpringApplication.run(NettyRpcProvider.class, args);
        new NettyServer("127.0.0.1",8080).startNettyServer();
    }

}
