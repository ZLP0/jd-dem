package com.example.jddemo.nettyrpc.v1.protocol.constants;


/**
 * 请求类型
 */
public enum ReqType {

    REQUEST((byte)1),//请求
    RESPONSE((byte)2),//响应
    HEARTBEAT((byte)3);

    private byte code;

    ReqType(byte code){
        this.code=code;
    }

    public byte code(){
        return this.code;
    }

    public static ReqType findByCode(int code){
        for(ReqType reqType: ReqType.values()){
            if(reqType.code==code){
                return reqType;
            }
        }
        return null;
    }
}
