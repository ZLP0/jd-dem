package com.example.jddemo.zookeeper.configcenter.event;

import com.example.jddemo.zookeeper.configcenter.listener.MyConfigurationPropertiesBeans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


/**
 * 事件监听
 */
@Component
public class EnvironmentChangeEventListener implements ApplicationListener<EnvironmentChangeEvent> {

    @Autowired
    MyConfigurationPropertiesBeans beans;

    @Autowired
    Environment  environment;

    @Override
    public void onApplicationEvent(EnvironmentChangeEvent environmentChangeEvent) {
        System.out.println("收到environment变更事件");
        this.beans.getFieldMapper().forEach((k,v)->{
            v.forEach(f->f.resetValue(environment));
        });
    }
}
