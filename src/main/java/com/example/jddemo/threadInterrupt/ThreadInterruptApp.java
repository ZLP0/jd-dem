package com.example.jddemo.threadInterrupt;

public class ThreadInterruptApp {

    static int count = 1;

    //  Thread.currentThread().interrupt()
    //  Thread.interrupted()   区别
    //  Thread.currentThread().interrupt()  给当前线程设置中断标识
    //  Thread.interrupted()  静态方法   清除线程中断标识   1 Thread.currentThread().interrupt() 后 2 第一次调用Thread.interrupted()  返回 true  以后返回false
    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                System.out.println("count:" + count);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
                if (count == 5) {
                    Thread.currentThread().interrupt();
                    System.out.println("中断表后：" + Thread.interrupted());
                    System.out.println("中断表示：" + Thread.interrupted());
                }
            }
        }).start();


    }
}
