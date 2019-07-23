package com.order_amqp.rabbitmqorder.config;

import org.springframework.context.annotation.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

@Configuration
public class AsyncExecutorConfiguration {
    @Value("${executor.pool.core.size}")
    private int poolSize;

    @Value("${executor.pool.max.size}")
    private int maxSize;

    @Value("${executor.queue.capacity}")
    private int capacity;

    @Bean
    public Executor sentinelSimpleAsync() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(poolSize);
        executor.setMaxPoolSize(maxSize);
        executor.setQueueCapacity(capacity);
        executor.setThreadNamePrefix("SentinelSimpleExecutor-");
        executor.initialize();
        return executor;
    }

    @Bean
    public Executor sentinelAsync() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(poolSize);
        executor.setMaxPoolSize(maxSize);
        executor.setQueueCapacity(capacity);
        executor.setThreadNamePrefix("SentinelSwapExecutor-");

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

}

