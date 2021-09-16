package com.example.jddemo.nettyrpc.v1.protocol.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


public class RequestHolder {
    /**
     * 请求 id
     */
    public static final AtomicLong REQUEST_ID=new AtomicLong();

    /**
     * key:请求 id
     * value:异步任务
     */
    public static final Map<Long,RpcFuture> REQUEST_MAP=new ConcurrentHashMap<>();
}
