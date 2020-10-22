package com.example.jddemo.unsafecas;

import sun.misc.Unsafe;

public class UnsafeApp {

    private  int  state=0;

    public static void main(String[] args) throws NoSuchFieldException {
        UnsafeApp app = new UnsafeApp();
        Unsafe unsafe = UnsafeUtils.getUnsafe();
        long offset = UnsafeUtils.getOffset(UnsafeApp.class, "state");
        // 如果 state==0  及更新为 1
        boolean result = unsafe.compareAndSwapInt(app, offset, 0, 1);
        System.out.println(app.state);
    }
}
