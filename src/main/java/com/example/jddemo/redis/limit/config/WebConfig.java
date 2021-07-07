package com.example.jddemo.redis.limit.config;

/**
 * 程序员  by dell
 * time  2021-02-23
 **/

import com.example.jddemo.logtrace.LogInterceptor;
import com.example.jddemo.redis.limit.FangshuaInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

@Configuration
public class WebConfig implements  WebMvcConfigurer {

    @Resource
    private FangshuaInterceptor interceptor;

    @Resource
    private LogInterceptor logInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor);//redis 防刷拦截器
        registry.addInterceptor(logInterceptor).addPathPatterns("/**/*");//log日志拦截器
    }
}