package com.example.jddemo.oauth2;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@EnableResourceServer // 表示是资源服务 解自动增加了一个类型为OAuth2AuthenticationProcessingFilter 的过滤器链
public class ResourcesConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("c1") // 接收访问的资源id
                .tokenServices(tokenServices()) //简单的编写一个资源控制类 注意放开权限管理
                .stateless(true); // 无状态的
    }

    /**
     * 远程验证Token信息
     *
     * @return
     */
    public ResourceServerTokenServices tokenServices() {
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setCheckTokenEndpointUrl("http://localhost:8082/oauth/check_token");
        tokenServices.setClientId("bobo");
        tokenServices.setClientSecret("secret");
        return tokenServices;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**").access("#oauth2.hasScope('all')")
                .anyRequest().permitAll() // 其他请求都放过
                .and().csrf().disable()// 不需要session管理
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}