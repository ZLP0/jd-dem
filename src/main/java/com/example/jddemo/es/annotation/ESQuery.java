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

        public static final String RANGE_FROM = "rangeFrom";//区间查询 start
        public static final String RANGE_TO = "rangeTo";//区间查询 end

    }
}
