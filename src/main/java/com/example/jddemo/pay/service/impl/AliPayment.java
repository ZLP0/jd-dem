package com.example.jddemo.pay.service.impl;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.example.jddemo.pay.constants.PayChannelEnum;
import com.example.jddemo.pay.context.AliPaymentContext;
import com.example.jddemo.pay.context.Context;
import com.example.jddemo.pay.request.AbstractRequest;
import com.example.jddemo.pay.request.PaymentRequest;
import com.example.jddemo.pay.validator.Validator;
import com.example.jddemo.response.AbstractResponse;
import com.example.jddemo.response.PaymentResponse;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class AliPayment extends BasePayment {


    /**
     * 执行 支付操作
     *
     * @param request
     * @param context
     * @return
     */
    @Override
    public AbstractResponse generalProcess(AbstractRequest request, Context context) {
        PaymentResponse paymentResponse = new PaymentResponse();
        PaymentRequest paymentRequest = (PaymentRequest) request;
        AliPaymentContext aliPaymentContext = (AliPaymentContext) context;
        try {
            AlipayTradePagePayResponse payResponse = Factory.Payment.Page()
                    .pay(
                            // 商品名
                            aliPaymentContext.getSubject(),
                            // 存在自己数据库中的唯一单号id
                            paymentRequest.getTradeNo(),
                            // 金额，精确到小数点后两位，范围为[0.01,9999999999999.99]
                            paymentRequest.getTotalFee().toString(),
                            // 付款完成后跳转网页，这里只是演示，我随便填的
                            "182.92.97.65"
                    );
            paymentResponse.setHtmlStr(payResponse.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return paymentResponse;
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
        AliPaymentContext aliPaymentContext = new AliPaymentContext();
        PaymentRequest paymentRequest = (PaymentRequest) request;
        aliPaymentContext.setSubject(paymentRequest.getSubject());
        aliPaymentContext.setOutTradeNo(paymentRequest.getTradeNo());
        aliPaymentContext.setTotalFee(paymentRequest.getTotalFee());
        aliPaymentContext.setUserId(paymentRequest.getUserId());
        return aliPaymentContext;
    }

    @Override
    public String getPayChannel() {
        return PayChannelEnum.ALI_PAY.getCode();
    }
}
