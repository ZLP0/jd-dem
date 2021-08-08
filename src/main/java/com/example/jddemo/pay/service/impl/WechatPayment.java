package com.example.jddemo.pay.service.impl;

import com.example.jddemo.jackson.JacksonUtils;
import com.example.jddemo.pay.config.WechatPaymentConfig;
import com.example.jddemo.pay.enums.PayEnum;
import com.example.jddemo.pay.context.Context;
import com.example.jddemo.pay.context.WechatPaymentContext;
import com.example.jddemo.pay.request.AbstractRequest;
import com.example.jddemo.pay.request.PaymentRequest;
import com.example.jddemo.pay.validator.Validator;
import com.example.jddemo.pay.wechat.HttpClientUtil;
import com.example.jddemo.pay.wechat.UtilDate;
import com.example.jddemo.pay.wechat.WeChatBuildRequest;
import com.example.jddemo.response.AbstractResponse;
import com.example.jddemo.response.ApiResponse;
import com.example.jddemo.response.PaymentResponse;
import com.github.wxpay.sdk.WXPay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;
import java.util.SortedMap;

@Slf4j
@Service
public class WechatPayment extends BasePayment {

    @Resource
    private WechatPaymentConfig wechatPaymentConfig;

    @Override
    public void prepare(AbstractRequest request, Context context) {
        super.prepare(request, context);
        SortedMap paraMap = context.getsParaTemp();
        WechatPaymentContext wechatPaymentContext = (WechatPaymentContext) context;
        paraMap.put("body", wechatPaymentContext.getBody());
        paraMap.put("out_trade_no", wechatPaymentContext.getOutTradeNo());
        //单位分
        paraMap.put("total_fee", wechatPaymentContext.getTotalFee().multiply(new BigDecimal("100")).intValue());
        paraMap.put("spbill_create_ip", wechatPaymentContext.getSpbillCreateIp());
        paraMap.put("appid", wechatPaymentConfig.getWechatAppid());
        paraMap.put("mch_id", wechatPaymentConfig.getWechatMch_id());
        paraMap.put("nonce_str", WeChatBuildRequest.getNonceStr());
        paraMap.put("trade_type", wechatPaymentContext.getTradeType());
        paraMap.put("product_id", wechatPaymentContext.getProductId());
        // 此路径是微信服务器调用支付结果通知路径
        paraMap.put("device_info", "WEB");
        paraMap.put("notify_url", wechatPaymentConfig.getWechatNotifyurl());
        //二维码的失效时间（5分钟）
        paraMap.put("time_expire", UtilDate.getExpireTime(30 * 60 * 1000L));
        String sign = WeChatBuildRequest.createSign(paraMap, wechatPaymentConfig.getWechatMchsecret());
        paraMap.put("sign", sign);
        log.info("微信生成sign:{}", JacksonUtils.toJson(paraMap));
        String xml = WeChatBuildRequest.getRequestXml(paraMap);
        wechatPaymentContext.setXml(xml);
    }

    @Override
    public AbstractResponse generalProcess(AbstractRequest request, Context context) {
        PaymentResponse response = new PaymentResponse();
        WechatPaymentContext wechatPaymentContext = (WechatPaymentContext) context;
        log.info("微信支付组装的请求参数:{}", wechatPaymentContext.getXml());
        String xml = HttpClientUtil.httpPost(wechatPaymentConfig.getWechatUnifiedOrder(), wechatPaymentContext.getXml());
        log.info("微信支付同步返回的结果:{}", xml);
        Map<String, String> resultMap = WeChatBuildRequest.doXMLParse(xml);
        if ("SUCCESS".equals(resultMap.get("return_code"))) {
            if ("SUCCESS".equals(resultMap.get("result_code"))) {
                response.setPrepayId(resultMap.get("prepay_id"));
                response.setCodeUrl(resultMap.get("code_url"));
                response.setCode(ApiResponse.CODE_SUCCESS);
            } else {
                String errMsg = resultMap.get("err_code") + ":" + resultMap.get("err_code_des");
                response.setCode(ApiResponse.CODE_WARN);
                response.setMessage("失败");
            }
        } else {
            response.setCode(ApiResponse.CODE_EXCEPTION);
            response.setMessage("失败");
        }

        WechatPaymentConfig config = new WechatPaymentConfig();
        WXPay wxpay = null;
        try {
            wxpay = new WXPay(config);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    public void afterProcess(AbstractRequest request, AbstractResponse respond, Context context) {

    }

    @Override
    protected Validator getValidator() {
        return null;
    }

    @Override
    protected Context createContext(AbstractRequest request) {
        WechatPaymentContext wechatPaymentContext = new WechatPaymentContext();
        PaymentRequest paymentRequest = (PaymentRequest) request;
        wechatPaymentContext.setOutTradeNo(paymentRequest.getTradeNo());
        wechatPaymentContext.setProductId(paymentRequest.getTradeNo());
        wechatPaymentContext.setSpbillCreateIp(paymentRequest.getSpbillCreateIp());
        wechatPaymentContext.setTradeType(PayEnum.NATIVE.getCode());
        wechatPaymentContext.setTotalFee(paymentRequest.getTotalFee());
        wechatPaymentContext.setBody(paymentRequest.getSubject());
        return wechatPaymentContext;
    }

    @Override
    public String getPayChannel() {
        return PayEnum.WECHAT_PAY.getCode();
    }
}
