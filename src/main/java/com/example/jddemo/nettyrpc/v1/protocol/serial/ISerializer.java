package com.example.jddemo.nettyrpc.v1.protocol.serial;

/**
 * 程序员  by dell
 * time  2021-09-16
 **/

public interface ISerializer {

    /**
     * 序列化
     *
     * @param obj
     * @param <T>
     * @return
     */
    <T> byte[] serialize(T obj);


    /**
     * 反序列化
     * @param data
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T desSerialize(byte[] data, Class<T> clazz);

    /**
     * 序列化类型
     * @return
     */
    byte getType();
}
