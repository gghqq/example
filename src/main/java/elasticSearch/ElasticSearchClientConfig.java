package elasticSearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName ElasticSearchClientConfig
 * @Description
 *
 * 可以通过spring 下面的data 目录, 查找到对象
 *
 *
 * @Author wpj
 * @Date 2021/6/15 21:32
 **/

@Configuration
public class ElasticSearchClientConfig {


    @Bean
    public RestHighLevelClient restHighLevelClient(){
        RestHighLevelClient http = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("10.200.50.108", 9200, "http")
                )
        );
        return  http;
    }

}
