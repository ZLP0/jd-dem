package com.example.jddemo.nio.selector;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO Selector模型
 */
public class NIOSelectorServerSocket implements Runnable {

    Selector selector;

    ServerSocketChannel serverSocketChannel;

    public NIOSelectorServerSocket(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        //采用Selector模型 设置非阻塞
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//注册到 selector上 获取客户端连接

    }

    @Override
    public void run() {

        while (!Thread.interrupted()) {
            try {
                selector.select();//阻塞等待事件
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

    private void disPatch(SelectionKey selectionKey) throws IOException {
        if (selectionKey.isAcceptable()) {//连接事件
            register(selectionKey);//注册读事件
        } else if (selectionKey.isReadable()) {//读事件
            read(selectionKey);
        } else if (selectionKey.isWritable()) {//写事件

        }
    }


    private void register(SelectionKey selectionKey) throws IOException {
        System.out.println("客户端连接 注册到 selector");
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();//获取客户端连接
        socketChannel.configureBlocking(false);//非阻塞
        socketChannel.register(selector, SelectionKey.OP_READ);//注册读事件 交给selector

    }


    private void read(SelectionKey selectionKey) throws IOException {
        //得到的是 SocketChannel
        SocketChannel channel = (SocketChannel) selectionKey.channel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        channel.read(byteBuffer);
        System.out.println("读取信息：" + new String(byteBuffer.array()));

    }

    public static void main(String[] args) throws IOException {
        NIOSelectorServerSocket nioSelectorServerSocket = new NIOSelectorServerSocket(8080);
        new Thread(nioSelectorServerSocket).start();

    }
}
