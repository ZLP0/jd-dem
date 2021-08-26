package com.example.jddemo.nio.reactorthread;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 处理 acceptor转发来的IO
 */
public class MultiHandler implements Runnable {
    private SocketChannel channel;

    public MultiHandler(SocketChannel channel) {
        this.channel = channel;
    }

    Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());//定义线程池

    @Override
    public void run() {

        processor();
    }

    private void processor() {
        executor.execute(new ReaderHandler(channel));

    }

    static class ReaderHandler implements Runnable {
        private SocketChannel channel;

        public ReaderHandler(SocketChannel channel) {
            this.channel = channel;
        }

        @Override
        public void run() {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int len = 0, total = 0;
            String msg = "";
            try {
                do {
                    len = channel.read(byteBuffer);
                    if (len > 0) {
                        total += len;
                        msg += new String(byteBuffer.array());
                    }

                } while (len > byteBuffer.capacity());
                System.out.println(channel.getRemoteAddress() + "：" + msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
