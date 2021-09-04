package com.example.jddemo.zookeeper.configcenter.listener;


import com.example.jddemo.zookeeper.configcenter.event.EnvironmentChangeEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.CuratorCacheListenerBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.Map;

/**
 * zk节点监听
 */
public class NodeDataChangeCuratorCacheListener implements CuratorCacheListenerBuilder.ChangeListener{

    private ConfigurableEnvironment configurableEnvironment;

    private ConfigurableApplicationContext applicationContext;

    public NodeDataChangeCuratorCacheListener(ConfigurableEnvironment configurableEnvironment, ConfigurableApplicationContext applicationContext) {
        this.configurableEnvironment = configurableEnvironment;
        this.applicationContext = applicationContext;
    }

    @Override
    public void event(ChildData oldNode, ChildData node) {
        System.out.println("数据变更");
        String resultData=new String (node.getData());
        ObjectMapper objectMapper=new ObjectMapper();
        try {
            //吧json格式的数据转换为map
            Map<String,Object> map=objectMapper.readValue(resultData, Map.class);
            //替换掉原来的PropertySource 更新到Environment中
            MapPropertySource mapPropertySource=new MapPropertySource("configService",map);
            configurableEnvironment.getPropertySources().replace("configService",mapPropertySource);

            //发送事件 通过反射机制 更新Bean中的属性值
            applicationContext.publishEvent(new EnvironmentChangeEvent(this));
            System.out.println("数据更新完成");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
