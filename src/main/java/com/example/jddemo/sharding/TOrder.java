package com.example.jddemo.sharding;

import lombok.Data;

import java.io.Serializable;

/**
 * (TOrder)实体类
 *
 * @author makejava
 * @since 2021-08-08 14:32:56
 */
@Data
public class TOrder implements Serializable {
    private static final long serialVersionUID = -78158101798595666L;

    private Integer userId;

    private String userName;

    private Long orderId;



}
