package com.example.jddemo.nettyrpc.v1.protocol.serial;

import java.io.*;

/**
 * 程序员  by dell
 * time  2021-09-16
 **/

public class JavaSerializer implements ISerializer {

    @Override
    public <T> byte[] serialize(T obj) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectInputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectInputStream.writeObject(obj);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }


    @Override
    public <T> T desSerialize(byte[] data, Class<T> clazz) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (T) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public byte getType() {
        return 0;
    }
}
