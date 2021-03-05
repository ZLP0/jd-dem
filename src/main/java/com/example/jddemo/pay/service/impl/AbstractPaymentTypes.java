package com.example.jddemo.pay.service.impl;

import com.example.jddemo.pay.context.Context;
import com.example.jddemo.pay.request.AbstractRequest;

import javax.annotation.PostConstruct;

public abstract class AbstractPaymentTypes {

    public abstract String getPayType();

    public abstract String pay(AbstractRequest request, Context context);


    @PostConstruct
    public void regist() {
        PaymentFactory.getInstance().registPaymentType(getPayType(),this);
    }

}
