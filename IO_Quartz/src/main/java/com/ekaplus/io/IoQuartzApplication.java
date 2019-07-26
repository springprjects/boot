package com.ekaplus.io;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

@SpringBootApplication
public class IoQuartzApplication {

	public static void main(String[] args) {
		SpringApplication.run(IoQuartzApplication.class, args);
	}
	
	
	@Bean
	public JobDetail jobDetail() {
	    return JobBuilder.newJob().ofType(SimpleJob.class)
	      .storeDurably()
	      .withIdentity("Qrtz_Job_Detail")  
	      .withDescription("Invoke Sample Job service...")
	      .build();
	}

	@Bean
	public Trigger trigger(JobDetail job) {
	    return TriggerBuilder.newTrigger().forJob(job)
	      .withIdentity("Qrtz_Trigger")
	      .withDescription("Sample trigger")
	      .withSchedule(CronScheduleBuilder.cronSchedule("0/3 * * ? * * *"))
	      .build();
	}
	
	
	
	/*@Bean
	public JobDetailFactoryBean jobDetail() {
	    JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
	    jobDetailFactory.setJobClass(SimpleJob.class);
	    jobDetailFactory.setDescription("Invoke Sample Job service...");
	    jobDetailFactory.setDurability(true);
	    return jobDetailFactory;
	}
	
	@Bean
	public SimpleTriggerFactoryBean trigger(JobDetail job) {
	    SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
	    trigger.setJobDetail(job);
	    trigger.setRepeatInterval(3600000);
	    trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
	    return trigger;
	}
	
	
	@Bean
	public Scheduler scheduler(Trigger trigger, JobDetail job, SchedulerFactoryBean factory) 
	  throws SchedulerException {
	    Scheduler scheduler = factory.getScheduler();
	    
	    if (scheduler.checkExists(job.getKey())){
	        scheduler.deleteJob(job.getKey());
	    }
	    
	    scheduler.scheduleJob(job, trigger);
	    scheduler.start();
	    return scheduler;
	}*/
	
	

}
