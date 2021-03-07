package com.example.jddemo.pay.config;

import com.github.wxpay.sdk.IWXPayDomain;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayConstants;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * @author 风骚的Michael 老师
 */
@Component
@Data
@PropertySource(value = {"classpath:application-pay.properties"})
@ConfigurationProperties(prefix = "wechatpay")
public class WechatPaymentConfig  extends WXPayConfig{

    //@Value("${wechat_appid}")
    private String wechatAppid;

    //@Value("${wechat_mch_id}")
    private String wechatMch_id;

    //默认为微信证书base64证书密文
    //@Value("${wechat_appsecret}")
    private String wechatAppsecret;

    //@Value("${wechat_mchsecret}")
    private String wechatMchsecret;

    //@Value("${wechat_notifyurl}")
    private String wechatNotifyurl;

    //@Value("${wechat_unified_order}")
    private String wechatUnifiedOrder;

    //@Value("${wechat_order_query}")
    private String checkOrderUrl;

    //@Value("${wechat_refund_url}")
    private String wechat_refund_url;

    //@Value("${wechat_refund_notify_url}")
    private String wechat_refund_notify_url;

    @Override
    public String getAppID() {
        return wechatAppid;
    }

    @Override
    public String getMchID() {
        return wechatMch_id;
    }

    @Override
    public String getKey() {
        return wechatMchsecret;
    }

    @Override
    public InputStream getCertStream() {
        return new ByteArrayInputStream(Base64.getDecoder().decode(wechatAppsecret));
    }

    @Override
    public IWXPayDomain getWXPayDomain() {
        IWXPayDomain iwxPayDomain = new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }
            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
        return iwxPayDomain;
    }
}
