package com.example.jddemo.websocket;

import java.io.Serializable;

public class Protocol implements Serializable {

    private int sendId;

    private int receiveId;

    private String msg;

    public int getSendId() {
        return sendId;
    }

    public void setSendId(int sendId) {
        this.sendId = sendId;
    }

    public int getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(int receiveId) {
        this.receiveId = receiveId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Protocol{" +
                "sendId=" + sendId +
                ", receiveId=" + receiveId +
                ", msg='" + msg + '\'' +
                '}';
    }
}
