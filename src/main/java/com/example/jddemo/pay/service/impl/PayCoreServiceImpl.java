package com.example.jddemo.pay.service.impl;

import com.example.jddemo.pay.request.PaymentRequest;
import com.example.jddemo.pay.service.PayCoreService;
import com.example.jddemo.pay.service.Payment;
import com.example.jddemo.response.ApiResponse;
import com.example.jddemo.response.PaymentResponse;
import org.springframework.stereotype.Service;

/**
 * 支付操作相关的服务
 */
@Service
public class PayCoreServiceImpl implements PayCoreService {

    @Override
    public ApiResponse<PaymentResponse> execPay(PaymentRequest request) {
        ApiResponse<PaymentResponse> apiResponse = new ApiResponse<>();
        Payment payment = PaymentFactory.getInstance().getPayment(request.getPayChannel());
        if (payment == null) {
            throw new RuntimeException("无可用Payment");
        }
        PaymentResponse process = payment.process(request);
        apiResponse.setData(process);

        return apiResponse;
    }
}
