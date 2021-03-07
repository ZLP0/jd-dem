package com.example.jddemo.pay.config;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlipayConfig {

    @Autowired
    AlipayProperties  alipayProperties;

    @Bean
    public Config getConfig() {
        Config config = new Config();
        // 网关协议
        config.protocol = alipayProperties.getProtocol().trim();
        // 网关地址
        config.gatewayHost = alipayProperties.getGatewayHost().trim();
        // 密钥加密方式
        config.signType = alipayProperties.getSignType().trim();
        // 应用识别码APPID
        config.appId = alipayProperties.getAppId().trim();
        // 应用私钥
        config.merchantPrivateKey = alipayProperties.getMerchantPrivateKey().trim();
        // 支付宝公钥
        config.alipayPublicKey = alipayProperties.getAlipayPublicKey().trim();
        //支付宝异步回调
        config.notifyUrl=alipayProperties.getNotifyUrl();

        // Factory全局只需配置一次
        Factory.setOptions(config);
        return config;
    }
}
