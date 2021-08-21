package com.example.jddemo.zookeeper.configcenter;

import com.example.jddemo.zookeeper.configcenter.propertysource.PropertySourceLocator;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;


public class ZookeeperApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>  {

    private List<PropertySourceLocator> propertySourceLocators;


    public ZookeeperApplicationContextInitializer() {
        //spring 默认类加载器
        ClassLoader defaultClassLoader = ClassUtils.getDefaultClassLoader();
        //SpringFactoriesLoader工厂的加载机制类似java提供的SPI机制一样，是Spring提供的一种加载方式。
        // 只需要在classpath路径下新建一个文件META-INF/spring.factories，
        // 并在里面按照Properties格式填写好接口和实现类即可通过SpringFactoriesLoader来实例化相应的Bean
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
        MutablePropertySources mutablePropertySources = configurableEnvironment.getPropertySources();
        //从 zookeeper上获取 数据
        for (PropertySourceLocator propertySourceLocator : propertySourceLocators) {
            PropertySource<?> propertySource = propertySourceLocator.locateCollection(configurableEnvironment,applicationContext);
            if (propertySource == null) {
                continue;
            }
            // 把属性源添加到Environment
            mutablePropertySources.addLast(propertySource);
        }
    }
}
