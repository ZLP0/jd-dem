package com.example.jddemo.threadlocal.context;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ContextCore extends AbstractContextCore {

    // 子线程不共享数据
    private ThreadLocal<Map<String, String>> local = new ThreadLocal<Map<String, String>>() {
        @Override
        protected Map<String, String> initialValue() {
            HashMap<String, String> map = new HashMap<>();
            map.put("Tcc", UUID.randomUUID().toString());
            return map;
        }
    };
    // 父子线程之间 共享数据
  /*
  InheritableThreadLocal<Map<String, String>> local=  new InheritableThreadLocal(){
        @Override
        protected Object initialValue(){
            HashMap<String, String> map = new HashMap<>();
            map.put("Tcc", UUID.randomUUID().toString());
            return map;
        }
    };*/


    public String get(String key) {
        return local.get().get(key);
    }
}
