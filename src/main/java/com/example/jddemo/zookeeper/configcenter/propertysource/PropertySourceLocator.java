package com.example.jddemo.zookeeper.configcenter.propertysource;


import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;

/**
 * 属性源 加载器  接口
 */
public interface PropertySourceLocator {

    /**
     * 收集属性资源
     * @param configurableEnvironment
     * @param applicationContext
     */
    PropertySource<?> locateCollection(ConfigurableEnvironment configurableEnvironment, ConfigurableApplicationContext applicationContext);
}
