package com.example.jddemo.pay.service;

import com.example.jddemo.pay.request.AbstractRequest;
import com.example.jddemo.response.AbstractResponse;

public interface Payment {

    /**
     * 发起交易执行的过程
     *
     * @param request
     * @return
     */
    <T extends AbstractResponse> T process(AbstractRequest request);

    public String getPayChannel();

}
