package com.example.jddemo;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.common.models.AlipayTradeRefundResponse;
import com.example.jddemo.jackson.JacksonUtils;
import com.example.jddemo.pay.request.PaymentRequest;
import com.example.jddemo.pay.service.PayCoreService;
import com.example.jddemo.response.CommonResponse;
import com.example.jddemo.response.PaymentResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 程序员  by dell
 * time  2020-12-09
 **/
@Slf4j
@RestController
public class App {

    Logger logger = LoggerFactory.getLogger(App.class);


    @Resource
    private PayCoreService payCoreService;

    public static void main(String[] args) {


    }

    @RequestMapping(value = "/payTest")
    public void testThread(HttpServletResponse response) throws IOException {

        response.setContentType("text/html;charset=UTF-8");
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setPayChannel("ali_pay");
        paymentRequest.setPayType("ali_pay_page");
        paymentRequest.setSubject("iphone13");
        paymentRequest.setTradeNo(UUID.randomUUID().toString());
        paymentRequest.setTotalFee(new BigDecimal(101));
        logger.info("订单号：======================：" + paymentRequest.getTradeNo());
        CommonResponse<PaymentResponse> objectCommonResponse = payCoreService.execPay(paymentRequest);

        System.out.println(objectCommonResponse);
        response.getWriter().write(objectCommonResponse.getData().getHtmlStr());
        response.getWriter().flush();


    }

    @RequestMapping("/refund")
    public void refund(String no, String amount) {
        try {
            AlipayTradeRefundResponse refund = Factory.Payment.Common().refund(no, amount);
            System.out.println(refund);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param request: 请求
     * @return java.lang.String
     * @description: 支付宝异步回调
     */
    @RequestMapping("/notify")
    public String notify(HttpServletRequest request) throws Exception {
        log.info("=========支付宝异步回调========param=【{}】", JacksonUtils.toJson(request.getParameterMap()));
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            params.put(name, request.getParameter(name));
            log.info(name + " = " + request.getParameter(name));
        }
        // 支付宝验签
        if (Factory.Payment.Common().verifyNotify(params)) {
            log.info("交易名称: " + params.get("subject"));
            log.info("交易状态: " + params.get("trade_status"));
            log.info("支付宝交易凭证号: " + params.get("trade_no"));
            log.info("商户订单号: " + params.get("out_trade_no"));
            log.info("交易金额: " + params.get("total_amount"));
            log.info("买家在支付宝唯一id: " + params.get("buyer_id"));
            log.info("买家付款时间: " + params.get("gmt_payment"));
            log.info("买家付款金额: " + params.get("buyer_pay_amount"));
        }
        return "success";
    }


    public void date() {
        LocalDate localDate = LocalDate.of(2021, 2, 2);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        Date start = Date.from(instant);
        long startTime = start.getTime();

        System.out.println("开始：" + startTime);

        localDate = LocalDate.of(2021, 2, 15);
        zone = ZoneId.systemDefault();
        instant = localDate.atStartOfDay().atZone(zone).toInstant();
        Date end = Date.from(instant);
        long endTime = end.getTime();

        System.out.println("结束：" + endTime);
    }


}
