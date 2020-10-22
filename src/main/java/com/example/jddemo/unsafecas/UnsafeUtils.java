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

    /**
     * 获取  指定类的属性 内存偏移量
     * @param classz         对应的class
     * @param attributeName  属性名称
     * @return
     * @throws NoSuchFieldException
     */
    public static long getOffset(Class classz, String attributeName) throws NoSuchFieldException {

        return getUnsafe().objectFieldOffset(classz.getDeclaredField(attributeName));
    }



}
