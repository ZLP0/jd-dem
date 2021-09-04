package com.example.jddemo.zookeeper.configcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

/**
 * demo 将custom.properties 属性文件 加载到环境中 @Value 可以获取到对应值
 */
public class DemoEnvironmentPostProcessor implements EnvironmentPostProcessor {
    private final Properties properties = new Properties();
    private String propertiesFile = "custom.properties";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Resource resource = new ClassPathResource(propertiesFile);
        MutablePropertySources propertySources = environment.getPropertySources();
        propertySources.addLast(loadProperties(resource));
        //多个文件同上
        //propertySources.addLast(loadProperties(resource));
    }

    private PropertySource<?> loadProperties(Resource resource) {
        if (!resource.exists()) {
            throw new RuntimeException("file not exist");
        }
        try {
            //custom.properties
            properties.load(resource.getInputStream());
            return new PropertiesPropertySource(resource.getFilename(), properties);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
