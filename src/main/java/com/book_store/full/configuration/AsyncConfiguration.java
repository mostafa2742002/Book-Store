package com.book_store.full.configuration;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfiguration {

    @Bean("taskExecutor")
    public Executor asynicTaskExecutor() {
        ThreadPoolTaskExecutor task_executor = new ThreadPoolTaskExecutor();
        task_executor.setCorePoolSize(2);
        task_executor.setMaxPoolSize(5);
        task_executor.setQueueCapacity(100);
        task_executor.setThreadNamePrefix("BookStore-");
        task_executor.initialize();
        return task_executor;
    }
}
