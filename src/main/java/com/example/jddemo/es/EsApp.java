package com.example.jddemo.es;

import com.example.jddemo.jackson.JacksonUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class EsApp {


    @Resource
    private RestHighLevelClient esClient;

    @RequestMapping(value = "/esTest")
    @ResponseBody
    public List<EsDataInsert> query() {
        EsData esData = new EsData();
        esData.setMessage("中华美好生活");
        //Location location = new Location();
        //location.setLon(116.39);
        //location.setLat(39.93);
        //location.setDistance("500");
        //location.setDistanceUnit("km");
        //esData.setLocation(location);
        try {
            AbstractBaseEs abstractBaseEs = new AbstractBaseEs();

            SearchRequest searchRequest = abstractBaseEs.buildSearchRequest(esData);

            SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);
            // 根据状态和数据条数验证是否返回了数据
            if (RestStatus.OK.equals(searchResponse.status()) && searchResponse.getHits().getTotalHits().value > 0) {
                SearchHits hits = searchResponse.getHits();
                List<EsDataInsert> esData1 = abstractBaseEs.DataConversion(hits, EsDataInsert.class);
                System.out.println(esData1);
                return esData1;
            }
        } catch (IllegalAccessException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping(value = "/termQuery")
    public void termQuery() {
        try {
            // 构建查询条件（注意：termQuery 支持多种格式查询，如 boolean、int、double、string 等，这里使用的是 string 的查询）
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            //searchSourceBuilder.query(QueryBuilders.termQuery("user", "tom"));
            // 创建查询请求对象，将查询对象配置到其中
            SearchRequest searchRequest = new SearchRequest("es_test");
            searchRequest.source(searchSourceBuilder);
            // 执行查询，然后处理响应结果
            SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);
            // 根据状态和数据条数验证是否返回了数据
            if (RestStatus.OK.equals(searchResponse.status()) && searchResponse.getHits().getTotalHits().value > 0) {
                SearchHits hits = searchResponse.getHits();
                for (SearchHit hit : hits) {
                    // 将 JSON 转换成对象
                    String source = hit.getSourceAsString();
                    EsData esData = JacksonUtils.fromIgnoreJson(source, EsData.class);
                    System.out.println(esData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/insert")
    public void insert() throws IOException {
        IndexRequest request = new IndexRequest("es_test2"); //索引 es_test
        // request.id("1"); //  id唯一 为空时es会默认创建 例如（"_id": "9VE8LHgBJWU1ODiL4r3M"）

        EsDataInsert esData = new EsDataInsert();
        esData.setUser("tom");
        esData.setPostDate("2021-03-14");
        esData.setMessage("我爱中华");
        esData.setFood(new String[]{"麻辣豆腐", "麻辣鱼头"});
        esData.setLocation(new double[]{116.421, 39.91});//经度 116.39  纬度 39.90
        EsDataInsert esData2 = new EsDataInsert();
        esData2.setUser("jack");
        esData2.setPostDate("2021-03-14");
        esData2.setMessage("中华人民共和国");
        esData2.setFood(new String[]{"小龙虾", "麻辣火锅"});
        esData2.setLocation(new double[]{117.39, 39.90});

        EsDataInsert esData3 = new EsDataInsert();
        esData3.setUser("丰台");
        esData3.setPostDate("2021-03-14");
        esData3.setMessage("我爱人民");
        esData3.setFood(new String[]{"小龙虾", "麻辣火锅"});
        esData3.setLocation(new double[]{118.39, 39.90});

        EsDataInsert esData4 = new EsDataInsert();
        esData4.setUser("生活");
        esData4.setPostDate("2021-03-14");
        esData4.setMessage("我爱生活");
        esData4.setLocation(new double[]{116.421, 39.91});//经度 116.39  纬度 39.90

        EsDataInsert esData5 = new EsDataInsert();
        esData5.setUser("生活2");
        esData5.setPostDate("2021-03-14");
        esData5.setMessage("生活很美好");
        esData5.setLocation(new double[]{116.421, 39.91});//经度 116.39  纬度 39.90

        ArrayList<EsDataInsert> list = new ArrayList<>();
        list.add(esData);
        list.add(esData2);
        list.add(esData3);
        list.add(esData4);
        list.add(esData5);

        for (EsDataInsert item : list) {
            request.source(JacksonUtils.toJson(item), XContentType.JSON); //以字符串形式提供的文档源
            IndexResponse indexResponse = esClient.index(request, RequestOptions.DEFAULT);
        }
        System.out.println("插入结束");
    }

    /**
     * 创建索引并指定字段类型
     *
     * @throws IOException
     */
    public void insert2() throws IOException {
        //1.创建索引的请求
        CreateIndexRequest request = new CreateIndexRequest("es_test3");

        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("properties");
            {
                //创建电影ID文档字段
                builder.startObject("movie_id");
                {
                    builder.field("type", "long");
                }
                builder.endObject();
                //创建电影名字文档字段
                builder.startObject("movie_name");
                {
                    builder.field("type", "text")
                            //插入时分词
                            .field("analyzer", "ik_smart")
                            //搜索时分词
                            .field("search_analyzer", "ik_max_word");
                }
                builder.endObject();
                //创建电影描述文档字段
                builder.startObject("movie_detail");
                {
                    builder.field("type", "text")
                            //插入时分词
                            .field("analyzer", "ik_smart")
                            //搜索时分词
                            .field("search_analyzer", "ik_max_word");
                }
                builder.endObject();
            }
            builder.endObject();
        }
        builder.endObject();
        request.mapping(builder);
        //2客户端执行请求，请求后获得响应
        CreateIndexResponse response = esClient.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(response);

    }

}
