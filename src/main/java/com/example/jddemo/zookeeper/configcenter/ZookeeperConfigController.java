package com.example.jddemo.zookeeper.configcenter;

import com.example.jddemo.zookeeper.configcenter.listener.RefreshScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 程序员  by dell
 * time  2021-08-20
 **/

@RestController
@RequestMapping(value = "/zookeeper")
@RefreshScope
public class ZookeeperConfigController {

    //@Value("${name}")
    private String name;

    //@Value("${sex}")
    private String sex;

    @RequestMapping(value = "/getConfig")
    public String getConfig() {
        return name + "   " + sex;
    }
}
