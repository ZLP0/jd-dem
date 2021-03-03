package com.example.jddemo.response;

public class PaymentResponse extends AbstractResponse{
    private static final long serialVersionUID = 436341660723282981L;
    /**构建html表单*/
    private String htmlStr;
    /**微信支付下单的返回id*/
    private String prepayId;
    /**微信支付下单构建的二维码地址*/
    private String codeUrl;

    public String getHtmlStr() {
        return htmlStr;
    }

    public void setHtmlStr(String htmlStr) {
        this.htmlStr = htmlStr;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }
}
