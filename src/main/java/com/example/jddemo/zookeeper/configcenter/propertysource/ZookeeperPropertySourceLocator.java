package com.example.jddemo.zookeeper.configcenter.propertysource;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySources;

import java.util.Map;

/**
 * 从zookeeper 加载属性 到PropertySourceLocator
 */
public class ZookeeperPropertySourceLocator implements PropertySourceLocator {

    private final CuratorFramework curatorFramework;

    private final String DATA_NODE = "/data";

    public ZookeeperPropertySourceLocator() {
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("182.92.97.65:2181")
                .sessionTimeoutMs(20000)
                .connectionTimeoutMs(20000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .namespace("config")
                .build();
        curatorFramework.start();
    }

    @Override
    public PropertySource<?> locateCollection() {
        //加载远程 zookeeper配置到 PropertySource
        CompositePropertySource composite = new CompositePropertySource("configService");
        try {
            //获取远程数据
            Map<String, Object> dataMap = this.getRemoteEnvironment();
            MapPropertySource mapPropertySource = new MapPropertySource("configService", dataMap);
            composite.addPropertySource(mapPropertySource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return composite;
    }

    private Map<String, Object> getRemoteEnvironment() throws Exception {
        String data = new String(curatorFramework.getData().forPath(DATA_NODE));
        //支持JSON格式
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(data, Map.class);
        return map;
    }
}
