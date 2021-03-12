package com.example.jddemo.es.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;


/**
 * @Deacription ElasticSearch 配置
 **/
@Configuration
@PropertySource(value = {"classpath:application-es.properties"})
public class ElasticSearchConfiguration {

    /**
     * 用户名
     */
    @Value("${elasticsearch.username}")
    private String  userName;

    /**
     * 密码
     */
    @Value("${elasticsearch.password}")
    private String  password;
    /**
     * 协议
     */
    @Value("${elasticsearch.schema}")
    private String schema;

    /**
     * 集群地址，如果有多个用“,”隔开
     */
    @Value("${elasticsearch.address}")
    private String address;

    /**
     * 连接超时时间
     */
    @Value("${elasticsearch.connectTimeout}")
    private int connectTimeout;

    /**
     * Socket 连接超时时间
     */
    @Value("${elasticsearch.socketTimeout}")
    private int socketTimeout;

    /**
     * 获取连接的超时时间
     */
    @Value("${elasticsearch.connectionRequestTimeout}")
    private int connectionRequestTimeout;

    /**
     * 最大连接数
     */
    @Value("${elasticsearch.maxConnectNum}")
    private int maxConnectNum;

    /**
     * 最大路由连接数
     */
    @Value("${elasticsearch.maxConnectPerRoute}")
    private int maxConnectPerRoute;

    @Bean(name = "restHighLevelClient")
    public RestHighLevelClient restHighLevelClient() {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(userName, password));
        // 拆分地址
        List<HttpHost> hostLists = new ArrayList<>();
        String[] hostList = address.split(",");
        for (String addr : hostList) {
            String host = addr.split(":")[0];
            String port = addr.split(":")[1];
            hostLists.add(new HttpHost(host, Integer.parseInt(port), schema));
        }
        // 转换成 HttpHost 数组
        HttpHost[] httpHost = hostLists.toArray(new HttpHost[]{});
        // 构建连接对象
        RestClientBuilder builder = RestClient.builder(httpHost);

        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
                    .setConnectTimeout(connectTimeout)
                    .setSocketTimeout(socketTimeout)
                    .setConnectionRequestTimeout(connectionRequestTimeout);
            httpClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
            httpClientBuilder.setMaxConnTotal(maxConnectNum);
            httpClientBuilder.setDefaultRequestConfig(requestConfigBuilder.build());
            httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            return httpClientBuilder;
        });

        return new RestHighLevelClient(builder);
    }
}