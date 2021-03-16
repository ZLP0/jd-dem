package com.example.jddemo.es.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 程序员  by dell
 * time  2021-03-15
 **/

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ESQuery {

    public String ColumnName();

    public String QueryType() default ConstantQueryType.TERM;

    public static class ConstantQueryType {

        public static final String TERM = "term";//精确查询
        public static final String TERMS = "terms";//精确查询 数组

        public static final String RANGE_FROM = "range_from";//区间查询 start   大于等于
        public static final String RANGE_TO = "range_to";//区间查询 end         小于等于

        public static final String WILD_CARD_LEFT = "wild_card_left";// 通配符 模糊查询   左模糊
        public static final String WILD_CARD_RIGHT = "wild_card_right";//通配符 模糊查询  右模糊
        public static final String WILD_CARD_ALL = "wild_card_all";//通配符 模糊查询      全模糊


    }
}
