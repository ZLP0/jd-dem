package com.example.jddemo.es;

import com.example.jddemo.jackson.JacksonUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;

@RestController
public class EsApp {


    @Resource
    private RestHighLevelClient esClient;

    @RequestMapping(value = "/es")
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
        IndexRequest request = new IndexRequest("es_test"); //索引 es_test
       // request.id("1"); //  id唯一 为空时es会默认创建 例如（"_id": "9VE8LHgBJWU1ODiL4r3M"）

        EsData esData = new EsData();
        esData.setUser("tom");
        esData.setPostDate("2021-03-14");
        esData.setMessage("测试数据");
        EsData esData2 = new EsData();
        esData2.setUser("jack");
        esData2.setPostDate("2021-03-14");
        esData2.setMessage("测试数据2");

        ArrayList<EsData> list = new ArrayList<>();
        list.add(esData);
        list.add(esData2);

        for (EsData item : list) {

            request.source(JacksonUtils.toJson(item), XContentType.JSON); //以字符串形式提供的文档源
            IndexResponse indexResponse = esClient.index(request, RequestOptions.DEFAULT);
        }
        System.out.println("插入结束");
    }

}
