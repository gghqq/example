package demo.elastic_job.config;


import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

import demo.elastic_job.DataflowJobDemo;
import demo.elastic_job.SimpleJobDemo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class ElasticJobConfig {


	@Resource
	private ZookeeperRegistryCenter zookeeperRegistryCenter;


	@Bean(initMethod = "init",name = "theSimpleJob")
	public JobScheduler simpleJob(SimpleJobDemo job) {
		//job cron表达式 分片数 备注
		return addSimpleJobScheduler(job,"*/2 * * * * ?",2,"simpleJob");
	}

	@Bean(initMethod = "init",name = "theDataflowJob")
	public JobScheduler dataflowJob(DataflowJobDemo job) {
		//job cron表达式 分片数 备注
		return addDataflowJobScheduler(job,"*/59 * * * * ?",2,"dataflowJobDemo");
	}



	private JobScheduler addDataflowJobScheduler(DataflowJob job,String cron,int count,String desc){
		LiteJobConfiguration jobConfiguration = LiteJobConfiguration
				.newBuilder(
						new DataflowJobConfiguration(
								JobCoreConfiguration.newBuilder(job.getClass().getName(), cron, count).misfire(false).description(desc).build(),
								job.getClass().getCanonicalName(),true)).overwrite(true).build();

		return new SpringJobScheduler(job, zookeeperRegistryCenter,jobConfiguration);
	}


	private JobScheduler addSimpleJobScheduler(SimpleJob job, String cron, int count, String desc){
		LiteJobConfiguration jobConfiguration = LiteJobConfiguration
				.newBuilder(
						new SimpleJobConfiguration(
								JobCoreConfiguration.newBuilder(job.getClass().getName(), cron, count).misfire(false).description(desc).build(),
								job.getClass().getCanonicalName())).overwrite(true).build();
		return new SpringJobScheduler(job, zookeeperRegistryCenter,jobConfiguration);
	}
}