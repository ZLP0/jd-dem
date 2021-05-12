package com.example.jddemo.es;

import com.example.jddemo.es.annotation.ESQuery;
import com.example.jddemo.es.annotation.ESTable;
import com.example.jddemo.es.param.Location;
import com.example.jddemo.jackson.JacksonUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import java.lang.reflect.Field;
import java.util.*;


/**
 * 程序员  by dell
 * time  2021-03-15
 **/
public abstract class AbstractBaseEs {
    /**
     * 构建查询请求
     *
     * @param param
     * @return
     */
    public SearchRequest buildSearchRequest(Object param, int from, int size) throws IllegalAccessException {
        ESTable esTable = param.getClass().getAnnotation(ESTable.class);
        if (null == esTable) {
            throw new RuntimeException(param.getClass() + "[类上未找到 ESTable 注解]");
        }
        if (StringUtils.isBlank(esTable.INDEX_NAME())) {
            throw new RuntimeException(param.getClass() + "[ESTable 注解 INDEX_NAME值为空]");
        }
        SearchSourceBuilder searchSourceBuilder = buildSearchSourceBuilder(param);
        searchSourceBuilder.from(from).size(size);//设置分页
        SearchRequest searchRequest = new SearchRequest(esTable.INDEX_NAME());
        //设置高亮展示
        buildHighlightBuilder(searchSourceBuilder);
        searchRequest.source(searchSourceBuilder);
        return searchRequest;
    }

    /**
     * 构建查询请求 scroll方式
     *
     * @param param
     * @param size
     * @param seconds
     * @return
     * @throws IllegalAccessException
     */
    public SearchRequest buildSearchScrollRequest(Object param, int size, TimeValue seconds) throws IllegalAccessException {
        SearchRequest searchRequest = this.buildSearchRequest(param, 0, 0);
        if (size == 0) {
            size = 1000;
        }
        if (null == seconds) {
            seconds = TimeValue.timeValueSeconds(10);
        }
        searchRequest.source().size(size);
        searchRequest.scroll(seconds);
        return searchRequest;
    }

    /**
     * 构建查询请求 scroll方式 (根据 scrollId 滚动查询 )
     *
     * @param scrollId
     * @return
     */
    public SearchScrollRequest buildSearchScrollRequest(String scrollId, TimeValue seconds) {
        SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId); //通过设置所需的scroll id和scroll间隔来创建搜索scroll请求
        if (null == seconds) {
            seconds = TimeValue.timeValueSeconds(10);
        }
        scrollRequest.scroll(seconds);
        return scrollRequest;
    }

    /**
     * 高亮展示
     *
     * @param searchSourceBuilder
     */
    public void buildHighlightBuilder(SearchSourceBuilder searchSourceBuilder) {
        HighlightBuilder highlightBuilder = new HighlightBuilder().field("*").requireFieldMatch(false);
        highlightBuilder.preTags("<span style=\"color:red\">");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);
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

    /**
     * 构建查询条件
     *
     * @param param
     * @return
     * @throws IllegalAccessException
     */
    private SearchSourceBuilder buildSearchSourceBuilder(Object param) throws IllegalAccessException {
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
            String columnName = esQuery.columnName();
            if (StringUtils.isBlank(columnName)) {
                throw new RuntimeException(field.getName() + "字段 [ESQUERY 注解 ColumnName 值为空] ");
            }
            Object value = field.get(param);
            if (null == value) {
                continue;
            }
            // boolQueryBuilder.filter()  boolQueryBuilder.must() filter比 must性能好
            switch (esQuery.queryType()) {
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
                        throw new RuntimeException(field.getName() + "字段 [ESQUERY 注解 QueryType 类型错误] ");
                    }
                    break;
                case ESQuery.ConstantQueryType.RANGE_FROM:
                    boolQueryBuilder.filter(QueryBuilders.rangeQuery(columnName).gte(value));
                    break;
                case ESQuery.ConstantQueryType.RANGE_TO:
                    boolQueryBuilder.filter(QueryBuilders.rangeQuery(columnName).lte(value));
                    break;
                case ESQuery.ConstantQueryType.WILD_CARD_LEFT:
                    boolQueryBuilder.filter(QueryBuilders.wildcardQuery(columnName, "*" + value));
                    break;
                case ESQuery.ConstantQueryType.WILD_CARD_RIGHT:
                    boolQueryBuilder.filter(QueryBuilders.wildcardQuery(columnName, value + "*"));
                    break;
                case ESQuery.ConstantQueryType.WILD_CARD_ALL:
                    boolQueryBuilder.filter(QueryBuilders.wildcardQuery(columnName, "*" + value + "*"));
                    break;
                case ESQuery.ConstantQueryType.MATCH_QUERY:
                    //ik_smart 最小分词  ik_max_word  最大分词
                    boolQueryBuilder.should(QueryBuilders.matchQuery(columnName, value).analyzer(esQuery.analyzerMode()));
                    break;
                case ESQuery.ConstantQueryType.GEO_DISTANCE:
                    if (!(value instanceof Location)) {
                        break;
                    }
                    Location location = (Location) value;
                    double lat = location.getLat();
                    double lon = location.getLon();
                    //默认查询1Km  默认单位Km
                    String distance = StringUtils.isBlank(location.getDistance()) ? "1" : location.getDistance();
                    if (lat == 0 || lon == 0) {
                        break;
                    }
                    DistanceUnit distanceUnit = null;
                    String locationDistanceUnit = location.getDistanceUnit();
                    if (StringUtils.equals("m", locationDistanceUnit)) {
                        distanceUnit = DistanceUnit.METERS;
                    } else if (StringUtils.equals("km", locationDistanceUnit)) {
                        distanceUnit = DistanceUnit.KILOMETERS;
                    } else {
                        // 默认千米
                        distanceUnit = DistanceUnit.KILOMETERS;
                    }
                    boolQueryBuilder.filter(QueryBuilders.geoDistanceQuery(columnName).point(lat, lon).distance(distance, distanceUnit));
                    // 距离排序
                    GeoDistanceSortBuilder sort = SortBuilders.geoDistanceSort(columnName, lat, lon);
                    sort.order(SortOrder.ASC);
                    searchSourceBuilder.sort(sort);
                    break;
                default:
                    break;
            }

        }
        searchSourceBuilder.query(boolQueryBuilder);
        return searchSourceBuilder;
    }

    public <T> List<T> DataConversion(SearchHits hits, Class<T> clazz) {
        if (hits == null || hits.getHits().length <= 0) {
            return Arrays.asList();
        }
        List<T> list = new ArrayList<>();
        for (SearchHit hit : hits) {
            // 将 JSON 转换成对象
            String source = hit.getSourceAsString();
            T obj = JacksonUtils.fromIgnoreJson(source, clazz);
            list.add(obj);
        }
        return list;
    }

}
