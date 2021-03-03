package com.example.jddemo.pay.service.impl;

import com.example.jddemo.pay.request.PaymentRequest;
import com.example.jddemo.pay.service.PayCoreService;
import com.example.jddemo.pay.service.Payment;
import com.example.jddemo.response.AbstractResponse;
import com.example.jddemo.response.CommonResponse;
import com.example.jddemo.response.PaymentResponse;
import org.springframework.stereotype.Service;

/**
 * 支付操作相关的服务
 */
@Service
public class PayCoreServiceImpl implements PayCoreService {

    @Override
    public CommonResponse<PaymentResponse> execPay(PaymentRequest request) {
        CommonResponse<PaymentResponse> commonResponse = new CommonResponse<>();
        Payment payment = PaymentFactory.getInstance().getPayment(request.getPayChannel());
        if (payment == null) {
            throw new RuntimeException("无可用Payment");
        }
        PaymentResponse process = payment.process(request);
        commonResponse.setData(process);

        return commonResponse;
    }
}
