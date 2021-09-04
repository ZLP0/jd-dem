package com.example.jddemo.nio.multireactor;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 多reactor 多线程模型
 */
public class Reactor implements Runnable {

    private Selector selector;

    private ConcurrentLinkedQueue<AsyncHandler> events = new ConcurrentLinkedQueue<>();


    public Reactor() throws IOException {
        this.selector = Selector.open();
    }

    @Override
    public void run() {

        while (true) {
            try {

                AsyncHandler handler;
                while ((handler = events.poll()) != null) {
                    SocketChannel socketChannel = handler.getSocketChannel();
                    socketChannel.configureBlocking(false);
                    SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_READ, handler);
                    handler.setKey(selectionKey);
                }

                selector.select();//等待客户端连接
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    //可能拿到的对象有两个 Acceptor、Handler
                    //第一次进来会胡取到 Acceptor  从而注册读事件 、读事件则会获取到Handler对象
                    Runnable attachment = (Runnable) key.attachment();
                    if (attachment != null) {
                        attachment.run();
                    }
                    iterator.remove();//移除 事件key
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public Selector getSelector() {
        return selector;
    }

    public void register(AsyncHandler handler) {
        events.offer(handler); //有一个事件注册
        selector.wakeup();
    }
}
