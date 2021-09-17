package com.example.jddemo.netty.promisedemo;

import io.netty.channel.DefaultEventLoop;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Promise;

import java.util.concurrent.ExecutionException;

/**
 * promise 的使用
 */
public class App {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Promise<String> future = new DefaultPromise(new DefaultEventLoop());

        new Thread(() -> {
            try {
                System.out.println("睡眠5s start");
                Thread.sleep(5000);
                future.setSuccess("成功");
                System.out.println("睡眠结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println("阻塞等待结果：");
        String result = future.get();//阻塞方法
        System.out.println("输出结果:" + result);

    }
}
