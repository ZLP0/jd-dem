package com.example.jddemo.nettyrpc.v1.protocol.constants;

import com.example.jddemo.nettyrpc.v1.protocol.core.Header;
import lombok.Data;

/**
 * 传输协议
 *
 * @param <T>
 */
@Data
public class RpcProtocol<T> {

    private Header header;
    private T content;
}
