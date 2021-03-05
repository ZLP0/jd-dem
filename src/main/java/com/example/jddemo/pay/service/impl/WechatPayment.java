package com.example.jddemo.pay.service.impl;

import com.example.jddemo.pay.constants.PayEnum;
import com.example.jddemo.pay.context.Context;
import com.example.jddemo.pay.request.AbstractRequest;
import com.example.jddemo.pay.validator.Validator;
import com.example.jddemo.response.AbstractResponse;

public class WechatPayment extends BasePayment {
    @Override
    public AbstractResponse generalProcess(AbstractRequest request, Context context) {
        return null;
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
        return null;
    }

    @Override
    public String getPayChannel() {
        return PayEnum.WECHAT_PAY.getCode();
    }
}
