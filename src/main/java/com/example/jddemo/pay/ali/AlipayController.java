package com.example.jddemo.pay.ali;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.alipay.easysdk.payment.wap.models.AlipayTradeWapPayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/pay")
public class AlipayController {

    // 电脑网页支付
    @GetMapping("/page")
    public Map<String, Object> pagePay() {
        try {
            // Payment.Page就是电脑网页支付的封装接口
            AlipayTradePagePayResponse response = Factory.Payment.Page()
                    .pay(
                            // 商品名
                            "来撒钱吧大兄弟",
                            // 存在自己数据库中的唯一单号id
                            String.valueOf(LocalTime.now().getNano()),
                            // 金额，精确到小数点后两位，范围为[0.01,9999999999999.99]
                            "9999.99",
                            // 付款完成后跳转网页，这里只是演示，我随便填的
                            "www.bilibili.com"
                    );
            return wrap(response.getBody());
        } catch (Exception e) {
            log.warn("调用异常，原因：{}", e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    // 手机网页支付
    @GetMapping("/wap")
    public Map<String, Object> wapPay() {
        try {
            // Payment.Wap就是手机网页支付的封装接口，注意他们的返回类型是不一样的
            AlipayTradeWapPayResponse response = Factory.Payment.Wap()
                    .pay(
                            // 商品名
                            "来继续撒",
                            // 存在自己数据库中的唯一单号id
                            String.valueOf(LocalTime.now().getNano()),
                            // 金额，要求都一样的，参考上面电脑网页支付的注释
                            "131499520.99",
                            // 未支付，中途退出时返回的网址
                            "www.csdn.com",
                            // 完成支付后返回的网址
                            "www.bilibili.com"
                    );
            return wrap(response.getBody());
        } catch (Exception e) {
            log.warn("调用异常，原因：{}", e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private Map<String, Object> wrap(Object data) {
        return new HashMap<String, Object>(3){{
            put("msg", "success");
            put("code", "20000");
            put("data", data);
        }};
    }
}
