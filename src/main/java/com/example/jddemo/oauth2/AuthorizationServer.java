package com.example.jddemo.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer //@EnableAuthorizationServer 表示是一个授权服务
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {


    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    //@Autowired
    //private AuthorizationCodeServices  authorizationCodeServices;

    /**
     * 授权服务的权限配置信息
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws
            Exception {
        security.checkTokenAccess("permitAll()") // 资源服务发送的检查Token是否合法的 请求放过
                .tokenKeyAccess("permitAll()") // 客户端发送的获取Token的服务方法
                .allowFormAuthenticationForClients(); // 支持客户端表单提交
    }

    /**
     * 访问服务的配置信息
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
        endpoints.authenticationManager(authenticationManager) //关联对应的认证器
                .allowedTokenEndpointRequestMethods(HttpMethod.POST, HttpMethod.GET)
                .tokenServices(tokenServices()) // 设置token相关的服务
               // .authorizationCodeServices(authorizationCodeServices)//授权码服务
        ;
    }

    /**
     * 客户端的配置信息
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws
            Exception {
        clients.inMemory()
                .withClient("bobo") // 客户端编号
                .redirectUris("http://www.baidu.com") // 重定义地址，携带授权码或者 Token信息
                .autoApprove(false) // 不自动授权，用户自己选择
                .scopes("all") // 作用域 all read writer
                .secret(new BCryptPasswordEncoder().encode("secret")) // 客户端的 密码
                // 授权服务支持的类型
                .authorizedGrantTypes("authorization_code"  //授权码模式
                        , "password"                        //密码模式
                        , "client_credentials"              // 客户端模式
                        , "implicit"                        //简化模式
                        , "refresh_token")  //刷新 token
                .resourceIds("c1"); // 要访问的资源的编号
    }

    /**
     * 设置Token的相关信息
     *
     * @return
     */
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(new InMemoryTokenStore()); // 存储方式
        tokenServices.setSupportRefreshToken(true); // 支持刷新
        tokenServices.setClientDetailsService(clientDetailsService); // token对应的客户端信息
        tokenServices.setAccessTokenValiditySeconds(7200); // 默认的有效期
        tokenServices.setRefreshTokenValiditySeconds(259200);//刷新令牌默认有效期三天
        return tokenServices;
    }


   // @Bean
   // public AuthorizationCodeServices authorizationCodeServices(){
   //     return new InMemoryAuthorizationCodeServices();
   // }
}
