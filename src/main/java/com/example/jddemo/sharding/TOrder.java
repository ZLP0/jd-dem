package com.example.jddemo.sharding;

import java.io.Serializable;

/**
 * (TOrder)实体类
 *
 * @author makejava
 * @since 2021-08-08 14:32:56
 */
public class TOrder implements Serializable {
    private static final long serialVersionUID = -78158101798595666L;

    private Integer userId;

    private String userName;

    private Integer orderId;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

}
