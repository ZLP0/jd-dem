package com.example.jddemo.netty.v3.message.codec;

import com.example.jddemo.netty.v3.message.protocol.Header;
import com.example.jddemo.netty.v3.message.protocol.MessageRecord;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

/**
 * 编码
 */
public class MessageRecordEncode extends MessageToByteEncoder<MessageRecord> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MessageRecord messageRecord, ByteBuf byteBuf) throws Exception {
        System.out.println("=====================开始编码=====================");
        Header header = messageRecord.getHeader();
        byteBuf.writeLong(header.getSessionId());//写入8个字节的 sessionId
        byteBuf.writeByte(header.getReqType());//写入1个字节 消息类型
        Object body = messageRecord.getBody();
        if (body != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(body);
            byte[] bytes = byteArrayOutputStream.toByteArray();

            byteBuf.writeInt(bytes.length);//写入消息长度 int占4个字节
            byteBuf.writeBytes(bytes);//写入消息内容
        } else {
            System.out.println("写入消息为空");
            byteBuf.writeInt(0);
        }

    }
}
