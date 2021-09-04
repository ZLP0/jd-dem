package com.example.jddemo.nio.reactor;


import java.io.IOException;

public class ReactorMain {
    public static void main(String[] args) {
        try {
            NIOReactorServerSocket nioReactorServerSocket = new NIOReactorServerSocket(8080);
            new Thread(nioReactorServerSocket).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
