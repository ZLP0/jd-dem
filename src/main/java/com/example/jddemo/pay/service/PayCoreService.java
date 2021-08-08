package com.example.jddemo.pay.service;


import com.example.jddemo.pay.request.PaymentRequest;
import com.example.jddemo.response.ApiResponse;
import com.example.jddemo.response.PaymentResponse;

/**
 * 支付操作相关的服务
 */
public interface PayCoreService {


    /**
     * 执行支付操作
     * @param request
     * @return
     */
   ApiResponse<PaymentResponse> execPay(PaymentRequest request);




}
