package com.example.jddemo.contracter;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ProtocolManager {

    final HashMap<Integer, AbstractProtocol> map = new HashMap();

    static final ProtocolManager protocolManager = new ProtocolManager();

    private ProtocolManager() {

    }

    public static ProtocolManager getInstance() {
        return protocolManager;
    }

    public void register(int type, AbstractProtocol protocol) {
        map.put(type, protocol);
    }

    public AbstractProtocol getProtocol(int type) {
        if (!map.containsKey(type)) {
            return null;
        }

        return map.get(type);
    }
}
