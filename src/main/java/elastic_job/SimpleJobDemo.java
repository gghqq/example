package elastic_job;


import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.springframework.stereotype.Component;



@Component
public class SimpleJobDemo implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println(String.format("------Thread ID: %s, 任务总片数: %s, " +
                        "当前分片项: %s.当前参数: %s,"+
                        "当前任务名称: %s.当前任务参数: %s"
                ,
                Thread.currentThread().getId(),
                shardingContext.getShardingTotalCount(),
                shardingContext.getShardingItem(),
                shardingContext.getShardingParameter(),
                shardingContext.getJobName(),
                shardingContext.getJobParameter()

        ));

        switch (shardingContext.getShardingItem()) {
            case 0:
                System.out.println("分片0执行的任务: " + shardingContext.getShardingParameter());
                break;
            case 1:
                System.out.println("分片1执行的任务: " + shardingContext.getShardingParameter());
                break;
            case 2:
                System.out.println("没有第三个分片");
                break;
        }

    }
}