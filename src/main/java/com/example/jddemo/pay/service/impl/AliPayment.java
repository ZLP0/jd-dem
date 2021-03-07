package com.example.jddemo.pay.service.impl;

import com.example.jddemo.pay.enums.PayEnum;
import com.example.jddemo.pay.context.AliPaymentContext;
import com.example.jddemo.pay.context.Context;
import com.example.jddemo.pay.request.AbstractRequest;
import com.example.jddemo.pay.request.PaymentRequest;
import com.example.jddemo.pay.validator.Validator;
import com.example.jddemo.response.AbstractResponse;
import com.example.jddemo.response.PaymentResponse;
import org.springframework.stereotype.Service;

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
        AbstractPaymentTypes paymentType = PaymentFactory.getInstance().getPaymentType((((PaymentRequest) request).getPayType()));
        String strHtml = paymentType.pay(request, context);
        paymentResponse.setHtmlStr(strHtml);
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
        return PayEnum.ALI_PAY.getCode();
    }
}
