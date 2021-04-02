package com.example.jddemo.oauth2;

import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

public class AuthorizationServerConfigurerAdapter implements AuthorizationServerConfigurer {



    public AuthorizationServerConfigurerAdapter() {
    }

    // 权限服务的相关配置
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

    }

    // 客户端信息的配置
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

    }

    // 服务请求的配置
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

    }


}
