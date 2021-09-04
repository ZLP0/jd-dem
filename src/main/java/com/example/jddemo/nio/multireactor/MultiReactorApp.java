package com.example.jddemo.nio.multireactor;


import java.io.IOException;

public class MultiReactorApp {


    private Reactor mainReactor;

    private int port;

    public MultiReactorApp(int port) throws IOException {
        this.mainReactor = new Reactor();
        this.port = port;
    }

    private void start() throws IOException {
        new Acceptor(mainReactor.getSelector(),this.port);
        new Thread(mainReactor).start();
    }

    public static void main(String[] args) throws IOException {

        MultiReactorApp multiReactorApp = new MultiReactorApp(8080);
        multiReactorApp.start();

    }


}
