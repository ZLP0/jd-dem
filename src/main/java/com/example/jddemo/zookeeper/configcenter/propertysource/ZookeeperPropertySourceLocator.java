package com.example.jddemo.zookeeper.configcenter.propertysource;


import com.example.jddemo.zookeeper.configcenter.listener.NodeDataChangeCuratorCacheListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

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
                .sessionTimeoutMs(200000)
                .connectionTimeoutMs(200000)
                .retryPolicy(new ExponentialBackoffRetry(100000, 3))
                .namespace("config")
                .build();
        curatorFramework.start();
    }

    @Override
    public PropertySource<?> locateCollection(ConfigurableEnvironment configurableEnvironment, ConfigurableApplicationContext applicationContext) {

        //加载远程 zookeeper配置到 PropertySource
        CompositePropertySource composite = new CompositePropertySource("configService");
        try {
            //获取远程数据
            Map<String, Object> dataMap = this.getRemoteEnvironment();
            MapPropertySource mapPropertySource = new MapPropertySource("configService", dataMap);
            composite.addPropertySource(mapPropertySource);
            //添加节点数据变更的事件监听
            addListener(configurableEnvironment,applicationContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return composite;
    }

    /**
     * 获取远端数据
     *
     * @return
     * @throws Exception
     */
    private Map<String, Object> getRemoteEnvironment() throws Exception {
        String data = new String(curatorFramework.getData().forPath(DATA_NODE));
        //支持JSON格式
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(data, Map.class);
        return map;
    }


    /**
     * 添加数据节点变更事件监听
     *
     * @param configurableEnvironment
     * @param applicationContext
     */
    private void addListener(ConfigurableEnvironment configurableEnvironment, ConfigurableApplicationContext applicationContext) {
        NodeDataChangeCuratorCacheListener ndc = new NodeDataChangeCuratorCacheListener(configurableEnvironment,applicationContext);
        CuratorCache curatorCache = CuratorCache.build(curatorFramework, DATA_NODE, CuratorCache.Options.SINGLE_NODE_CACHE);
        CuratorCacheListener listener = CuratorCacheListener
                .builder()
                .forChanges(ndc).build();
        curatorCache.listenable().addListener(listener);
        curatorCache.start();
    }
}
