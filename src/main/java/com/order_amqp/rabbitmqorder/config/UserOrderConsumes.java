package com.order_amqp.rabbitmqorder.config;

import com.order_amqp.rabbitmqorder.common.RabbitMqEnum;
import com.order_amqp.rabbitmqorder.component.UserOrderListener;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  rabbitmq消费者配置文件
 */
@Configuration
@AutoConfigureAfter(RabbitmqConfig.class)
public class UserOrderConsumes {

    @Autowired
    private UserOrderListener userOrderListener;

    @Value("${spring.rabbitmq.listener.simple.concurrency}")
    private int concurrency;
    @Value("${spring.rabbitmq.listener.simple.max-concurrency}")
    private int maxConcurrency;
    @Value("${spring.rabbitmq.listener.simple.prefetch}")
    private int prefetch;

    /**
     *  构建多个消费者
     */
    @Bean
    public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        // 并发设置
        container.setConcurrentConsumers(concurrency);
        container.setMaxConcurrentConsumers(maxConcurrency);
        container.setPrefetchCount(prefetch);
        // TODO 队列名称以及消息监听待配置
        container.setQueueNames(RabbitMqEnum.QueueEnum.USER_ORDER_QUEUE.getCode());
        container.setMessageListener(userOrderListener);
        return container;
    }
}
