package elasticsearch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import demo.App;
import elasticSearch.User;
import javafx.application.Application;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName esTest
 * @Description
 * @Author wpj
 * @Date 2021/6/15 21:28
 **/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class EsTest {

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    @Test //测试索引的创建
    public void testCreateIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("kuang_index"); //创建索引请求
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT); //客户端执行请求,获得响应
        System.out.println(response);
    }

    @Test
        //测试获取索引
    public void testExistIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("oas_rece_acc");
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    @Test
        //测试删除索引
    public void testDeleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("kuang_index");
        AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(delete);
    }


    @Test
        //测试添加文档
    public void testAddDocument() throws IOException {
        User user = new User("狂神说", 3);
        IndexRequest request = new IndexRequest("kuang_index"); //创建请求

        request.id("1");  //规则 put/kuang_index/_doc/1
        request.timeout(TimeValue.timeValueMillis(1));
        request.source(JSON.toJSONString(user), XContentType.JSON);
        //发送请求  获取响应结果
        IndexResponse index = client.index(request, RequestOptions.DEFAULT);
        System.out.println(index.toString());
        System.out.println(index.status());
    }

    @Test
        //获取文档,判断是否存在
    public void testIsExists() throws IOException {
        GetRequest getRequest = new GetRequest("kuang_index");
        getRequest.fetchSourceContext(new FetchSourceContext(false)); //忽略_source上下文
        getRequest.storedFields("_none_");

        boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println(exists);


    }


    @Test
//获取文档的信息
    public void testGetDocument() throws IOException {
        GetRequest getRequest = new GetRequest("kuang_index", "1");
        GetResponse documentFields = client.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(documentFields.getSourceAsString()); //打印文档内容
        System.out.println(documentFields); //返回的全部内容
    }

    @Test
//获取文档的信息
    public void testUpdateDocument() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest("kuang_index", "1");
        updateRequest.timeout(TimeValue.timeValueSeconds(3));

        User user = new User("狂神说java", 18);
        updateRequest.doc(JSON.toJSONString(user), XContentType.JSON);

        UpdateResponse update = client.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(update.status());
    }


    @Test
    //删除文档的信息
    public void testDeleteDocument() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("qdp-wain-order", "1432221553743224832"); //假如不存在 not_found
        deleteRequest.timeout(TimeValue.timeValueSeconds(3));
        DeleteResponse delete = client.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(delete.status());
    }


    @Test
        //SearchRequest 搜索请求
        //SearchSourceBuilder 条件构造
        //HighLightBuilder 高亮构造器
        //TermQueryBuilder 精确查询构造器
        //MatchAllQueryBuilder 匹配全部构造器

    public void testSearch() throws IOException {
        SearchRequest request = new SearchRequest("oas_receivables");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery(); //匹配所有
        //查询条件,使用QueryBuilders 快速构建    boolQuery.must()    .mustNot   should
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("business_type", "9"); //精确匹配
        RangeQueryBuilder sn_create_time = QueryBuilders.rangeQuery("sn_create_time").gte("1614700800000");//大于等于
        RangeQueryBuilder sn_create_time1 = QueryBuilders.rangeQuery("sn_create_time").lte("1614700800000");//小于等于
        QueryBuilders.fuzzyQuery("name","wang").fuzziness(Fuzziness.ONE); //差一个字符也能匹配出来例如 wang1 wang2
        AggregationBuilders.avg("business_type_avg").field("business_type");//平均  max  min
        AggregationBuilders.terms("business_type_group").field("business_type");//分组
        //条件
        searchSourceBuilder.query(sn_create_time);
        searchSourceBuilder.query(termQueryBuilder);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchSourceBuilder.sort("sn_create_time", SortOrder.DESC);//排序
        searchSourceBuilder.fetchSource("business_type","business_type"); //前者select  business_type from  后者select !business_type from


//        //分页
//        searchSourceBuilder.from(1);
//        searchSourceBuilder.size(10);
        //高亮
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        highlightBuilder.field("title"); //高亮字段
//        highlightBuilder.requireFieldMatch(false); //关闭多个高亮,只高亮第一个
//        highlightBuilder.preTags("<span style='color:red'>");
//        highlightBuilder.postTags("</span>");
//        searchSourceBuilder.highlighter(highlightBuilder);

        request.source(searchSourceBuilder);
        SearchResponse search = client.search(request, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(search.getHits()));
        System.out.println("++++++++++++++++循环遍历+++++++++++++++++");
        for (SearchHit hit : search.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
//            Map<String, HighlightField> highlightFields = hit.getHighlightFields();// 获取高亮字段
//            HighlightField title = highlightFields.get("title");
//            Text[] fragments = title.fragments();
//            String n_title = "";
//            for (Text fragment : fragments) {
//                n_title += fragment;
//            }
//            sourceAsMap.put("title",n_title); //高亮内容替换原来内容
            System.out.println(JSONObject.toJSONString(sourceAsMap));

        }
    }

    @Test
        //批量插入数据
    public void testBulkRequest() throws IOException {
        BulkRequest request = new BulkRequest();
        request.timeout("10s");
        ArrayList<User> users = Lists.newArrayList(new User("kuangshen1", 3),
                new User("kuangshen2", 2),
                new User("kuangshen3", 3),
                new User("kkk", 4));

        for (int i = 0; i < users.size(); i++) {
            request.add(new IndexRequest("kuang_index") // .不同操作对应不同请求 DeleteRequest
                    .id(i + "")
                    .source(JSON.toJSONString(users.get(i)), XContentType.JSON)
            );
        }
        //批处理请求
        BulkResponse bulk = client.bulk(request, RequestOptions.DEFAULT);
        System.out.println(bulk.hasFailures()); //是否失败  返回false代表成功

    }

}
