package com.example.jddemo.nettyrpc.v1.protocol.constants;



public enum SerialType {

    JSON_SERIAL((byte)1),//json序列化
    JAVA_SERIAL((byte)2);//java序列化

    private byte code;

    SerialType(byte code){
        this.code=code;
    }

    public byte code(){
        return this.code;
    }
}
