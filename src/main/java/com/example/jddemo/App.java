package com.example.jddemo;

import com.example.jddemo.pay.request.PaymentRequest;
import com.example.jddemo.pay.service.PayCoreService;
import com.example.jddemo.pay.service.impl.PaymentFactory;
import com.example.jddemo.response.CommonResponse;
import com.example.jddemo.response.PaymentResponse;
import org.apache.poi.ss.usermodel.DataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.SortedMap;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 程序员  by dell
 * time  2020-12-09
 **/

@RestController
public class App {

    Logger logger = LoggerFactory.getLogger(App.class);

    ExecutorService executor = Executors.newFixedThreadPool(1);

    static ReentrantLock lock = new ReentrantLock(true);

    static int count = 1;

    @Resource
    private PayCoreService payCoreService;

    public static void main(String[] args) {

        PaymentFactory instance = PaymentFactory.getInstance();
        System.out.println(instance);
    }

    @RequestMapping(value = "/payTest")
    public void testThread(HttpServletResponse response) throws IOException {

        response.setContentType("text/html;charset=UTF-8");
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setPayChannel("ali_pay");
        paymentRequest.setSubject("iphone12");
        paymentRequest.setTradeNo(UUID.randomUUID().toString());
        paymentRequest.setTotalFee(new BigDecimal(100));
        CommonResponse<PaymentResponse> objectCommonResponse = payCoreService.execPay(paymentRequest);

        System.out.println(objectCommonResponse);
        response.getWriter().write(objectCommonResponse.getData().getHtmlStr());
        response.getWriter().flush();

    }

    public void thread(String str) {


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
