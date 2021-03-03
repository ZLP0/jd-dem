package com.example.jddemo.pay.validator;


/**
 * 数据验证接口类
 *
 * @author
 */
public interface Validator<T> {
    /**
     * 数据验证
     *
     * @param request
     */
    void validate(T request);
}
