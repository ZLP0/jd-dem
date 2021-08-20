package com.example.jddemo.zookeeper.configcenter;

import com.example.jddemo.zookeeper.configcenter.propertysource.PropertySourceLocator;
import com.example.jddemo.zookeeper.configcenter.propertysource.ZookeeperPropertySourceLocator;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.ClassUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


public class ZookeeperApplicationContextInitializer implements ApplicationContextInitializer {

    private List<PropertySourceLocator> propertySourceLocators;


    public ZookeeperApplicationContextInitializer() {
        //spring 默认类加载器
        ClassLoader defaultClassLoader = ClassUtils.getDefaultClassLoader();
        //加载 PropertySourceLocator 接口 spi扩展
        this.propertySourceLocators = new ArrayList<>(SpringFactoriesLoader.loadFactories(PropertySourceLocator.class, defaultClassLoader));
    }

    /**
     * bean 实例化之前的操作
     *
     * @param applicationContext
     */
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment configurableEnvironment = applicationContext.getEnvironment();
        //从 zookeeper上获取 数据
        for (PropertySourceLocator propertySourceLocator : propertySourceLocators) {
            PropertySource<?> propertySource = propertySourceLocator.locateCollection();
            if (propertySource == null) {
                continue;
            }
            // 把属性源添加到Environment
            configurableEnvironment.getPropertySources().addLast(propertySource);
        }
    }
}
