package com.example.jddemo.pay.service.impl;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.example.jddemo.pay.constants.PayEnum;
import com.example.jddemo.pay.context.AliPaymentContext;
import com.example.jddemo.pay.context.Context;
import com.example.jddemo.pay.request.AbstractRequest;
import com.example.jddemo.pay.request.PaymentRequest;
import org.springframework.stereotype.Service;

@Service
public class AliPaymentPage extends AbstractPaymentTypes {


    @Override
    public String getPayType() {
        return PayEnum.ALI_PAY_PAGE.getCode();
    }

    @Override
    public String pay(AbstractRequest request, Context context) {
        PaymentRequest paymentRequest = (PaymentRequest) request;
        AliPaymentContext aliPaymentContext = (AliPaymentContext) context;
        AlipayTradePagePayResponse payResponse = null;
        try {
            payResponse = Factory.Payment.Page()
                    .pay(
                            // 商品名
                            aliPaymentContext.getSubject(),
                            // 存在自己数据库中的唯一单号id
                            paymentRequest.getTradeNo(),
                            // 金额，精确到小数点后两位，范围为[0.01,9999999999999.99]
                            paymentRequest.getTotalFee().toString(),
                            // 付款完成后跳转网页，这里只是演示，我随便填的
                            "http://localhost:8082/notify"
                    );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return payResponse.getBody();
    }
}
