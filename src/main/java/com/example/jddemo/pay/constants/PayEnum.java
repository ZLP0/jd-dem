package com.example.jddemo.pay.constants;


/**
 * 支付渠道
 */
public enum PayEnum {

    ALI_PAY("ali_pay", "支付宝支付渠道"),
    WECHAT_PAY("wechat_pay", "微信支付渠道"),
    ALI_REFUND("ali_refund", "支付宝退款渠道"),
    WECHAT_REFUND("wechat_refund", "微信退款渠道"),

    ALI_PAY_PAGE("ali_pay_page", "支付宝网页端"),
    ALI_PAY_WAP("ali_pay_wap", "支付宝手机端");


    private String code;

    private String desc;

    PayEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


    public static boolean isExists(String code) {
        for (PayEnum item : values()) {
            if (item.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }
}
