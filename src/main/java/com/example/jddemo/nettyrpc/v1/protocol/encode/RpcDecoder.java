package com.example.jddemo.nettyrpc.v1.protocol.encode;

import com.example.jddemo.nettyrpc.v1.protocol.constants.ReqType;
import com.example.jddemo.nettyrpc.v1.protocol.constants.RpcConstant;
import com.example.jddemo.nettyrpc.v1.protocol.constants.RpcProtocol;
import com.example.jddemo.nettyrpc.v1.protocol.core.Header;
import com.example.jddemo.nettyrpc.v1.protocol.core.RpcRequest;
import com.example.jddemo.nettyrpc.v1.protocol.core.RpcResponse;
import com.example.jddemo.nettyrpc.v1.protocol.serial.ISerializer;
import com.example.jddemo.nettyrpc.v1.protocol.serial.SerializerManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 解码
 */
@Slf4j
public class RpcDecoder extends ByteToMessageDecoder {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        log.info("========begin RpcDecoder==========");

        if(in.readableBytes()< RpcConstant.HEAD_TOTAL_LENGTH){
            return;
        }
        in.markReaderIndex(); //标记读取开始索引
        short maci=in.readShort(); //读取2个字节的magic
        if(maci!=RpcConstant.MAGIC){
            throw new IllegalArgumentException("Illegal request parameter 'magic',"+maci);
        }

        byte serialType=in.readByte(); //读取一个字节的序列化类型
        byte reqType=in.readByte(); //读取一个字节的消息类型
        long requestId=in.readLong(); //读取请求id
        int dataLength=in.readInt(); //读取数据报文长度

        if(in.readableBytes()<dataLength){
            in.resetReaderIndex();
            return ;
        }
        //读取消息体的内容
        byte[] content=new byte[dataLength];
        in.readBytes(content);

        Header header=new Header(maci,serialType,reqType,requestId,dataLength);
        ISerializer serializer=SerializerManager.getSerializer(serialType);
        ReqType rt=ReqType.findByCode(reqType);
        switch (rt){
            case REQUEST:
                RpcRequest request=serializer.desSerialize(content,RpcRequest.class);
                RpcProtocol<RpcRequest> reqProtocol=new RpcProtocol<>();
                reqProtocol.setHeader(header);
                reqProtocol.setContent(request);
                out.add(reqProtocol);
                break;
            case RESPONSE:
                RpcResponse response=serializer.desSerialize(content, RpcResponse.class);
                RpcProtocol<RpcResponse> resProtocol=new RpcProtocol<>();
                resProtocol.setHeader(header);
                resProtocol.setContent(response);
                out.add(resProtocol);
                break;
            case HEARTBEAT:
                //TODO
                break;
            default:
                break;
        }

    }
}
