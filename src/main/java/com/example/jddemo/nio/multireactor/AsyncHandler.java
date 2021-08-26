package com.example.jddemo.nio.multireactor;


import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class AsyncHandler implements Runnable {

    private SocketChannel socketChannel;

    private SelectionKey selectionKey;

    StringBuilder stringBuilder=new StringBuilder();
    ByteBuffer inputBuffer=ByteBuffer.allocate(1024);
    ByteBuffer outputBuffer=ByteBuffer.allocate(1024);

    public AsyncHandler(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }


    @Override
    public void run() {
        try {
            if (selectionKey.isReadable()) {
                read();
            } else if (selectionKey.isWritable()) {
                //write();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void read() throws IOException {
        inputBuffer.clear();
        int read = socketChannel.read(inputBuffer);
        if(inputBufferComplete(read)){
            System.out.println(Thread.currentThread().getName()+": Server端收到客户端的请求消息："+stringBuilder.toString());
            outputBuffer.put(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
            //this.selectionKey.interestOps(SelectionKey.OP_WRITE);
        }
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }


    public void setKey(SelectionKey selectionKey) {
        this.selectionKey = selectionKey;
    }

    private boolean inputBufferComplete(int bytes) throws EOFException {
        if(bytes>0){
            inputBuffer.flip();
            while(inputBuffer.hasRemaining()){
                byte ch=inputBuffer.get(); //得到输入的字符
                if(ch==3) { //表示Ctrl+c
                    throw new EOFException();
                }else if(ch=='\r'||ch=='\n'){
                    return true;
                }else {
                    stringBuilder.append((char)ch);
                }
            }
        }else if(bytes==1){
            throw new EOFException();
        }
        return false;
    }
}
