package com.example.jddemo.netty.v1;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * netty
 */
public class NettyBasicServerExample {

    public static void main(String[] args) {
        // 我们要创建两个EventLoopGroup，
        // 一个是boss专门用来接收连接，可以理解为处理accept事件，
        //另一个是worker，可以关注除了accept之外的其它事件，处理子任务。
        //上面注意，boss线程一般设置一个线程，设置多个也只会用到一个，而且多个目前没有应用场景，
        // worker线程通常要根据服务器调优，如果不写默认就是cpu的两倍
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        //工作线程
        NioEventLoopGroup workGroup = new NioEventLoopGroup(4);

        try {
            //构建Netty  Server API
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workGroup)
                    //配置Server的通道，相当于NIO中的ServerSocketChannel
                    .channel(NioServerSocketChannel.class)
                    //childHandler表示给worker那些线程配置了一个处理器，
                    // 配置初始化channel，也就是给worker线程配置对应的handler，当收到客户端的请求时，分配给指定的handler处理
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //todo
                            //1：编码处理
                            //2：协议处理
                            //3：消息处理
                            //4：心跳处理
                            socketChannel.pipeline()
                                    //添加handler，也就是具体的IO事件处理器
                                    .addLast("handler1", new NormalMessageHandler());
                        }
                    });

            //由于默认情况下是NIO异步非阻塞，所以绑定端口后，通过sync()方法阻塞直到连接建立
            //绑定端口并同步等待客户端连接（sync方法会阻塞，直到整个启动过程完成）
            ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();//绑定端口
            System.out.println("嗨  netty server start success : port 8080");
            // 等待服务端监听端口关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放线程资源
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }

}
