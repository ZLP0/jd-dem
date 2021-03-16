package com.example.jddemo.es;

import com.example.jddemo.common.utils.ExecutorsUtil;
import com.example.jddemo.es.annotation.ESQuery;
import com.example.jddemo.es.annotation.ESTable;
import com.example.jddemo.jackson.JacksonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 程序员  by dell
 * time  2021-03-15
 **/
public class AbstractEs {

    /**
     * 查询条件转化
     *
     * @param param
     * @return
     */
    public SearchRequest buildSearchRequest(Object param) throws IllegalAccessException {
        ESTable esTable = param.getClass().getAnnotation(ESTable.class);
        if (null == esTable) {
            throw new RuntimeException("类上未找到 ESTable 注解");
        }
        if (StringUtils.isBlank(esTable.INDEX_NAME())) {
            throw new RuntimeException("ESTable 注解 INDEX_NAME值为空 ");
        }
        SearchRequest searchRequest = new SearchRequest(esTable.INDEX_NAME());

        SearchSourceBuilder searchSourceBuilder = buildSearchSourceBuilder(param);
        searchRequest.source(searchSourceBuilder);
        return searchRequest;
    }

    //termQuery(字段.keyword,"value")：单条件精确查询。
    //termsQuery(字段.keyword,"value1","value2","...")：多条件精确查询，取并集。
    //rangeQuery("字段").from("start").to("end"); 范围查询，查询指定字段处于start到end范围的值，闭区间（不包括start、end）。
    //rangeQuery("字段").from("start", false).to("end", false); 开区间范围查询。
    //rangeQuery("字段").lt("value"); 小于value的值，小于等于是lte、大于是gt、大于等于是gte。
    //组合多条件查询，将上面的可以进行组合，使用：must必须、mustNot必须不、should类似于or进行连接。
    //wildcardQuery("字段","*value*")：模糊查询，支持通配符。
    //queryStringQuery("value").field("字段");不使用通配符的模糊查询，左右匹配。
    //multiMatchQuery("字段","value1","value2")：多字段模糊查询
    public SearchSourceBuilder buildSearchSourceBuilder(Object param) throws IllegalAccessException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //构建多条件查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        Field[] fields = param.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            ESQuery esQuery = field.getAnnotation(ESQuery.class);
            if (null == esQuery) {
                continue;
            }
            String columnName = esQuery.ColumnName();
            if (StringUtils.isBlank(columnName)) {
                throw new RuntimeException(field.getName() + "字段 ESQUERY 注解 ColumnName 值为空 ");
            }
            Object value = field.get(param);
            if (null == value) {
                continue;
            }
            // boolQueryBuilder.filter()  boolQueryBuilder.must() filter比 must性能好
            switch (esQuery.QueryType()) {
                case ESQuery.ConstantQueryType.TERM:
                    boolQueryBuilder.filter(QueryBuilders.termQuery(columnName, value));
                    break;
                case ESQuery.ConstantQueryType.TERMS:
                    //判断返回类型是否是集合类型
                    if (Collection.class.isAssignableFrom(field.getType())) {
                        boolQueryBuilder.filter(QueryBuilders.termsQuery(columnName, value));
                    } else if (field.getType().isArray()) {
                        List<Object> list = Arrays.asList(value);
                        boolQueryBuilder.filter(QueryBuilders.termsQuery(columnName, list));
                    } else {
                        throw new RuntimeException(field.getName() + "字段 ESQUERY 注解 QueryType 类型错误 ");
                    }
                    break;
                case ESQuery.ConstantQueryType.RANGE_FROM:
                    boolQueryBuilder.filter(QueryBuilders.rangeQuery(esQuery.ColumnName()).gte(value));
                    break;
                case ESQuery.ConstantQueryType.RANGE_TO:
                    boolQueryBuilder.filter(QueryBuilders.rangeQuery(esQuery.ColumnName()).lte(value));
                    break;
                case ESQuery.ConstantQueryType.WILD_CARD_LEFT:
                    boolQueryBuilder.filter(QueryBuilders.wildcardQuery(esQuery.ColumnName(), "*" + value));
                    break;
                case ESQuery.ConstantQueryType.WILD_CARD_RIGHT:
                    boolQueryBuilder.filter(QueryBuilders.wildcardQuery(esQuery.ColumnName(), value + "*"));
                    break;
                case ESQuery.ConstantQueryType.WILD_CARD_ALL:
                    boolQueryBuilder.filter(QueryBuilders.wildcardQuery(esQuery.ColumnName(), "*" + value + "*"));
                    break;
                default:
                    break;
            }

        }
        searchSourceBuilder.query(boolQueryBuilder);
        return searchSourceBuilder;
    }

    public List<T> DataConversion(SearchHits hits, Class<T> clazz) {
        ArrayList<T> list = new ArrayList<T>();
        for (SearchHit hit : hits) {
            // 将 JSON 转换成对象
            String source = hit.getSourceAsString();
            T obj = JacksonUtils.fromIgnoreJson(source, T.class);
            list.add(obj);
        }
        return list;
    }

}
