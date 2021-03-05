package com.example.jddemo.pay.service.impl;

import com.example.jddemo.pay.service.Payment;

import java.util.HashMap;
import java.util.Map;

public class PaymentFactory {

    public static Map<String, Payment> paymentMap = new HashMap<>();

    public static Map<String, AbstractPaymentTypes> paymentTypeMap = new HashMap<>();

    public static final PaymentFactory PAYMENT_FACTORY = new PaymentFactory();

    private PaymentFactory() {
    }

    public static PaymentFactory getInstance() {
        return PAYMENT_FACTORY;
    }

    public void regist(String type, Payment obj) {

        if (type == null || obj == null) {
            //表明向方法返回了一个不合法或者不正确的参数
            throw new IllegalArgumentException(" type or obj can not null");
        }
        Map<String, Payment> temp = new HashMap();
        if (paymentMap == null) {
            temp = new HashMap<>(8);
        } else {
            temp = new HashMap(paymentMap);
        }
        if (temp.containsKey(String.valueOf(type))) {
            throw new IllegalArgumentException(" key 已存在");
        }
        temp.put(type, obj);
        paymentMap = temp;
    }

    public void registPaymentType(String type, AbstractPaymentTypes obj) {

        if (type == null || obj == null) {
            //表明向方法返回了一个不合法或者不正确的参数
            throw new IllegalArgumentException(" type or obj can not null");
        }
        Map<String, AbstractPaymentTypes> temp = new HashMap();
        if (paymentTypeMap == null) {
            temp = new HashMap<>(8);
        } else {
            temp = new HashMap(paymentTypeMap);
        }
        if (temp.containsKey(String.valueOf(type))) {
            throw new IllegalArgumentException(" key 已存在");
        }
        temp.put(type, obj);
        paymentTypeMap = temp;
    }

    public Payment getPayment(String key) {
        return paymentMap.get(key);
    }

    public AbstractPaymentTypes getPaymentType(String key) {
        return paymentTypeMap.get(key);
    }
}
