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

    /**
     * 列名
     * @return
     */
    public String columnName();

    /**
     * 查询类型  精确查询 模糊查询 全文检索 等。。。。。
     * @return
     */
    public String queryType() default ConstantQueryType.TERM;

    /**
     * 分词模式  只针对 es text 类型
     * @return
     */
    public String analyzerMode() default analyzerMode.IK_MAX_WORD;

    public static final class ConstantQueryType {

        public static final String TERM = "term";//精确查询
        public static final String TERMS = "terms";//精确查询 数组或者List

        public static final String RANGE_FROM = "range_from";//区间查询 start   大于等于
        public static final String RANGE_TO = "range_to";//区间查询 end         小于等于

        public static final String WILD_CARD_LEFT = "wild_card_left";// 通配符 模糊查询   左模糊
        public static final String WILD_CARD_RIGHT = "wild_card_right";//通配符 模糊查询  右模糊
        public static final String WILD_CARD_ALL = "wild_card_all";//通配符 模糊查询      全模糊

        public static final String GEO_DISTANCE = "geo_distance";//com.example.jddemo.es.param.Location 上使用

        public static final String MATCH_QUERY = "match_query"; //全文检索 分词查询


    }

    /**
     * 分词
     */
    public static final class analyzerMode {

        /**
         * 最大分词
         */
        public static final String IK_MAX_WORD="ik_max_word";

        /**
         * 最小分词
         */
        public static final String IK_SMART="ik_smart";

    }
}
