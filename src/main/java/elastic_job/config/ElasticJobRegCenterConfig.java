package elastic_job.config;


import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;

//@Configuration
@ConditionalOnExpression("'127.0.0.1:2181'.length() > 0")
// @ConditionalOnExpression("'${regCenter.serverList}'.length() > 0")
public class ElasticJobRegCenterConfig {

    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter regCenter(
//            @Value("${regCenter.serverList}") final String serverList,
//            @Value("${regCenter.namespace}") final String namespace
    ) {
        return new ZookeeperRegistryCenter(new ZookeeperConfiguration("127.0.0.1", "example"));
    }
}