package com.example.jddemo.nio.reactorthread;

import com.example.jddemo.nio.reactor.Acceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO  单线程 Reactor模型  多线程处理handler
 */
public class NIOMultiReactorServerSocket implements Runnable{

   private final   Selector selector;

    private final  ServerSocketChannel serverSocketChannel;

    public NIOMultiReactorServerSocket(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        //采用Selector模型 设置非阻塞
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT,new MultiAcceptor(selector,serverSocketChannel));//注册到 selector上 获取客户端连接

    }


    @Override
    public void run() {
        while (!Thread.interrupted()){
            try {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();//返回事件列表
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    //说明有连接进来
                    SelectionKey selectionKey = iterator.next();
                    disPatch(selectionKey);
                    iterator.remove();//移除当前就绪事件
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void disPatch(SelectionKey selectionKey) {
        //可能拿到的对象有两个 Acceptor、Handler
        //第一次进来会胡取到 Acceptor  从而注册读事件 、读事件则会获取到Handler对象
        Runnable runnable = (Runnable) selectionKey.attachment();
        runnable.run();
    }
}
