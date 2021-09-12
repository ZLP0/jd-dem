package com.example.jddemo.netty.v3.message.codec;


import com.example.jddemo.netty.v3.message.protocol.Header;
import com.example.jddemo.netty.v3.message.protocol.MessageRecord;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * 解码
 */
public class MessageRecordDecode extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        MessageRecord record = new MessageRecord();
        Header header = new Header();
        header.setSessionId(byteBuf.readLong());//读取8个字节
        header.setReqType(byteBuf.readByte());//读取1个字节
        header.setLength(byteBuf.readInt());//读取4个字节，消息内容长度
        record.setHeader(header);
        if (header.getLength() > 0) {
            byte[] contents = new byte[header.getLength()];
            byteBuf.readBytes(contents);
            //java 对象流  读取对象
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(contents);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            record.setBody(objectInputStream.readObject());
            System.out.println("反序列化结果：" + record);
            list.add(record);
        } else {
            System.out.println("消息内容为空");
        }

    }
}
