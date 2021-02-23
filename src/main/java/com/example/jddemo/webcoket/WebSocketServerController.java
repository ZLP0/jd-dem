package com.example.jddemo.webcoket;

import com.example.jddemo.jackson.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@ServerEndpoint("/websocket/{sid}")
public class WebSocketServerController {

    // 保存用户session id作为key
    private static ConcurrentHashMap<String, Session> groupMemberInfoMap = new ConcurrentHashMap<>();

    // 收到消息调用的方法，群成员发送消息
    @OnMessage
    public void onMessage(String protocolMsg) {
        //  Protocol protocol = JSON.parseObject(protocolMsg, Protocol.class);
        Protocol protocol = JacksonUtils.fromIgnoreJson(protocolMsg, Protocol.class);
        String receiveId = String.valueOf(protocol.getReceiveId());
        Session session = groupMemberInfoMap.get(receiveId);
        try {
            session.getBasicRemote().sendText("用户" + protocol.getSendId() + ":" + protocol.getMsg());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 建立连接调用的方法，成员加入
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        groupMemberInfoMap.put(sid, session);
        System.out.println("Connection connected");
        System.out.println("sid: {" + sid + "}sessionList size: {" + groupMemberInfoMap.size() + "}");
    }

    // 关闭连接调用的方法，成员退出
    @OnClose
    public void onClose(Session session, @PathParam("sid") String sid) {
        groupMemberInfoMap.remove(session);
        System.out.println("Connection closed");
        System.out.println("sid: {" + sid + "}sessionList size: {" + groupMemberInfoMap.size() + "}");
    }

    // 传输消息错误调用的方法
    @OnError
    public void OnError(Throwable error) {
        System.out.println("Connection error");
    }
}