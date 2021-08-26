package com.example.jddemo.nio.reactorthread;


import com.example.jddemo.nio.reactor.NIOReactorServerSocket;

import java.io.IOException;

public class MultiReactorMain {
    public static void main(String[] args) {
        try {
            NIOReactorServerSocket nioReactorServerSocket = new NIOReactorServerSocket(8080);
            new Thread(nioReactorServerSocket).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
