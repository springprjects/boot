package com.ekaplus.io;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class BatchConfig {
	
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	JobLauncher jobLauncher;

/*	@Bean
	public Job importUserJob() {
		return jobBuilderFactory.get("hello").start(helloWorldStep(stepBuilderFactory)).build();
	}

	@Bean
	public Step helloWorldStep(StepBuilderFactory stepBuilders) {
		return stepBuilders.get("helloWorldStep").<User, String>chunk(10).faultTolerant().skipLimit(2).skip(FlatFileParseException.class).reader(reader()).processor(processor())
				.writer(writer()).build();
	}

	@Bean
	public UserItemProcessor processor() {
		return new UserItemProcessor();
	}

	@Bean
	public FlatFileItemWriter<String> writer() {
		return new FlatFileItemWriterBuilder<String>().name("greetingItemWriter")
				.resource(new FileSystemResource("target/test-outputs/greetings.txt"))
				.lineAggregator(new PassThroughLineAggregator<>()).build();
	}*/
	
	@Bean
	public FlatFileItemReader<User> reader() {
		return new FlatFileItemReaderBuilder<User>().name("personItemReader")
				.resource(new ClassPathResource("users.csv")).delimited()
				.names(new String[] { "name", "age", "address" }).linesToSkip(1).targetType(User.class).build();
	}

	@Scheduled(cron = "0 */1 * * * ?")
	public void perform() throws Exception {
		JobParameters params = new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		jobLauncher.run(importUserJob(), params);

	}

	

	@Autowired
	public DataSource dataSource;

	@Bean
	public Job importUserJob() {
		return jobBuilderFactory.get("importUserJob").incrementer(new RunIdIncrementer()).flow(step1()).end().build();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<User, User>chunk(10).faultTolerant().skipPolicy(skipPolicy()).reader(reader()).processor(jdbcProcessor())
				.writer(jdbcBatchItemWriter()).build();
	}
	
	@Bean
	public ExceptionSkipPolicy skipPolicy() {
		return new ExceptionSkipPolicy();
	}

	@Bean
	public UserItemJDBCProcessor jdbcProcessor() {
		return new UserItemJDBCProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<User> jdbcBatchItemWriter() {
		JdbcBatchItemWriter<User> writer = new JdbcBatchItemWriter<User>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
		writer.setSql("INSERT INTO user (name, age, address) VALUES (:name, :age, :address);");
		writer.setDataSource(dataSource);
		return writer;
	}

}
