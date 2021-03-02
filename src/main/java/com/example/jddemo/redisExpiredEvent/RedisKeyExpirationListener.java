package com.example.jddemo.redisExpiredEvent;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * ******注意注意******
 * 修改redis server 配置文件，开启keyt过期听通知配置
 * notify-keyspace-events Ex
 */
//@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * 需要注意的是：
     * 过期监听消息中返回的是，过期的键的key值，是没有返回value的
     *
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();
        System.out.println(expiredKey);
    }
}
