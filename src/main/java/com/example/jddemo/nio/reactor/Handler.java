package com.example.jddemo.nio.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 处理 acceptor转发来的IO
 */
public class Handler implements Runnable {
    private SocketChannel channel;

    public Handler(SocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int len = 0, total = 0;
        String msg = "";

        try {
            /*handler 是串行阻塞的
            Thread.sleep(10000);*/
            do {

                len= channel.read(byteBuffer);
            if(len>0){
                total+=len;
                msg+=new String(byteBuffer.array());
            }

            } while (len > byteBuffer.capacity());
            System.out.println(channel.getRemoteAddress()+"："+msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
