package com.example.jddemo.es;

import com.example.jddemo.es.annotation.ESQuery;
import com.example.jddemo.es.annotation.ESTable;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchRequest.source(searchSourceBuilder);

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
            if (ESQuery.ConstantQueryType.TERM.equals(esQuery.QueryType())) {
                searchSourceBuilder.query(QueryBuilders.termQuery(columnName, value));
            }
            if (ESQuery.ConstantQueryType.TERMS.equals(esQuery.QueryType())) {
                //判断返回类型是否是集合类型
                if (Collection.class.isAssignableFrom(field.getType())) {
                    searchSourceBuilder.query(QueryBuilders.termsQuery(columnName, value));
                } else if (field.getType().isArray()) {
                    List<Object> list = Arrays.asList(value);
                    searchSourceBuilder.query(QueryBuilders.termsQuery(columnName, list));
                }else {
                    throw new RuntimeException(field.getName() + "字段 ESQUERY 注解 QueryType 类型错误 ");
                }
                continue;
            }
        }
        return null;
    }
}
