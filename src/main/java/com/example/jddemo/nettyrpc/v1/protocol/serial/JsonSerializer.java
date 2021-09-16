package com.example.jddemo.nettyrpc.v1.protocol.serial;

import com.alibaba.fastjson.JSON;
import com.example.jddemo.nettyrpc.v1.protocol.constants.SerialType;


public class JsonSerializer implements ISerializer {

    @Override
    public <T> byte[] serialize(T obj) {
        return JSON.toJSONString(obj).getBytes();
    }

    @Override
    public <T> T desSerialize(byte[] data, Class<T> clazz) {
        return JSON.parseObject(new String(data), clazz);
    }


    @Override
    public byte getType() {
        return SerialType.JSON_SERIAL.code();
    }
}
