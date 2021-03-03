package com.example.jddemo.pay.service;


import com.example.jddemo.pay.request.PaymentRequest;
import com.example.jddemo.response.CommonResponse;
import com.example.jddemo.response.PaymentResponse;
import org.apache.poi.ss.formula.functions.T;

/**
 * 支付操作相关的服务
 */
public interface PayCoreService {


    /**
     * 执行支付操作
     * @param request
     * @return
     */
   CommonResponse<PaymentResponse> execPay(PaymentRequest request);




}
