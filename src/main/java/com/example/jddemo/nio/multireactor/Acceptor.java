package com.example.jddemo.nio.multireactor;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Acceptor implements Runnable {

    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    private final int POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private Executor subReactorExecutor = Executors.newFixedThreadPool(POOL_SIZE);

    //多个子 reactor
    private Reactor[] subReactors = new Reactor[POOL_SIZE];

    int handerNext=0;

    public Acceptor(Selector selector, int port) throws IOException {
        this.selector = selector;
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(8080));//绑定端口
        serverSocketChannel.configureBlocking(false);//非阻塞
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, this);//注册连接事件
        System.out.println("Main reactor Acceptor:Listening on port " + port);
        initSubReactors();
    }

    private void initSubReactors() throws IOException {
        for (int i = 0; i < subReactors.length; i++) {
            subReactors[i] = new Reactor();
            subReactorExecutor.execute(subReactors[i]);//运行线程
        }
    }

    @Override
    public void run() {

        try {
            SocketChannel socketChannel = serverSocketChannel.accept();//获取客户端连接

            if(socketChannel!=null){
                socketChannel.write(ByteBuffer.wrap("Multiply Reactor Patterm\r\nreactor> ".getBytes()));
                System.out.println(Thread.currentThread().getName()+": Main-Reactor-Acceptor:"+socketChannel.getLocalAddress()+"连接");
                Reactor subReactor=subReactors[handerNext];
                subReactor.register(new AsyncHandler(socketChannel));
                if(++handerNext==subReactors.length){
                    handerNext=0;
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
