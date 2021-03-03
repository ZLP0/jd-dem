package com.example.jddemo.pay.service.impl;

import com.example.jddemo.pay.context.Context;
import com.example.jddemo.pay.request.AbstractRequest;
import com.example.jddemo.pay.service.Payment;
import com.example.jddemo.pay.validator.Validator;
import com.example.jddemo.response.AbstractResponse;

import javax.annotation.PostConstruct;
import java.util.SortedMap;
import java.util.TreeMap;

public abstract class BasePayment implements Payment {

    /**
     * 核心处理器
     *
     * @param request
     * @param <T>
     * @return
     */
    @Override
    public <T extends AbstractResponse> T process(AbstractRequest request) {
        AbstractResponse response = null;
        //创建上下文
        Context context = createContext(request);
        //验证
        //getValidator().validate(request);
        //准备
        prepare(request, context);
        //执行
        response = generalProcess(request, context);
        //善后
        afterProcess(request, response, context);
        return (T) response;
    }

    public abstract AbstractResponse generalProcess(AbstractRequest request, Context context);

    /***
     * 基本业务处理完成后执行的后续操作
     * @param request
     * @param respond
     * @param context
     * @return
     */
    public abstract void afterProcess(AbstractRequest request, AbstractResponse respond,Context context);

    /**
     * 为下层的支付渠道的数据做好准备
     *
     * @param request
     * @param context
     */
    public  void prepare(AbstractRequest request, Context context){
        SortedMap<String, Object> sParaTemp = new TreeMap<String, Object>();
        context.setsParaTemp(sParaTemp);
    };

    protected abstract Validator getValidator();

    protected abstract Context createContext(AbstractRequest request);


    @PostConstruct
    public void regist() {
        PaymentFactory.getInstance().regist(getPayChannel(),this);
    }

}
