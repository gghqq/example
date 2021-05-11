package demo.elastic_job;


import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * stockJob.cron为定时任务的cron表达式；
 * stockJob.shardingTotalCount为任务的分数量（即同时同时开几个定时任务）；
 * stockJob.shardingItemParameters为任务分片携带的参数；
 */
@Component
public class DataflowJobDemo implements DataflowJob {


    @Override
    public List fetchData(ShardingContext shardingContext) {
        List<String> list = Lists.newArrayList("抓取到的数据1","抓取到的数据2");
        return list;
    }

    @Override
    public void processData(ShardingContext shardingContext, List list) {
        List<String> datas = list;
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
        //分片执行任务
        switch (shardingContext.getShardingItem()) {
            case 0:
                System.out.println("分片0执行的任务: " +datas.get(0));
                break;
            case 1:
                System.out.println("分片1执行的任务: " +datas.get(1));
                break;
            case 2:
                System.out.println("没有第三个分片");
                break;
        }

    }
}