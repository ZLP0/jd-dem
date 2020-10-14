package com.example.jddemo.unsafecas;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeUtils {

    //获取unsafe 实例
    public static Unsafe getUnsafe()   {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取 内存偏移量
    public static long getOffset(Unsafe unsafe, Class classz, String attributeName) throws NoSuchFieldException {
        return unsafe.objectFieldOffset(classz.getDeclaredField(attributeName));
    }



}
