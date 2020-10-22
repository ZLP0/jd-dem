package com.example.jddemo.threadlocal;

import com.example.jddemo.threadlocal.context.Context;

public class App {


    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {//启动三个线程
            Thread t = new Thread() {
                @Override
                public void run() {
                    String tcc = Context.getXID();
                    System.out.println(Thread.currentThread().getName()+"="+tcc);
                    new Thread(()->{
                        String  tcc1 = Context.getXID();
                        System.out.println(Thread.currentThread().getName()+"="+tcc1);

                    }).start();

                }
            };
            t.start();
        }



    }



}
