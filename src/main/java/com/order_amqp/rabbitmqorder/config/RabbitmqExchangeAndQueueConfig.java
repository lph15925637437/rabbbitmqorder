package com.order_amqp.rabbitmqorder.config;

import com.order_amqp.rabbitmqorder.common.RabbitMqEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  配置好交换机,队列以及其绑定关系
 */
@Configuration
@AutoConfigureAfter(RabbitmqConfig.class)
public class RabbitmqExchangeAndQueueConfig {
    private static final Logger logger = LoggerFactory.getLogger(RabbitmqExchangeAndQueueConfig.class);

    /**
     *  user_order_exchange交换机
     */
    @Bean(name = "userOrderExchange")
    public DirectExchange userOrderExchange(RabbitAdmin rabbitAdmin){
        DirectExchange directExchange = new DirectExchange(RabbitMqEnum.ExchangeEnum.DIRECT_EXCHANGE.getCode());
        rabbitAdmin.declareExchange(directExchange);
        logger.info("用户订单交换机初始化完成");
        return directExchange;
    }

    /**
     *  user_order_queue队列
     */
    @Bean(name = "userOrderQueue")
    public Queue userOrderQueue(RabbitAdmin rabbitAdmin){
        Queue queue = new Queue(RabbitMqEnum.QueueEnum.USER_ORDER_QUEUE.getCode());
        rabbitAdmin.declareQueue(queue);
        logger.info("用户订单队列初始化完成");
        return queue;
    }

    /**
     *  user_order_routeKey路由键进行交换机和队列的绑定
     */
    @Bean(name = "userOrderBinding")
    public Binding userOrderBinding(Queue userOrderQueue, DirectExchange userOrderExchange, RabbitAdmin rabbitAdmin){
        Binding binding = BindingBuilder.bind(userOrderQueue).to(userOrderExchange).with(RabbitMqEnum.RoutingEnum.USER_ORDER_ROUTING.getCode());
        rabbitAdmin.declareBinding(binding);
        logger.info("用户订单绑定完成");
        return binding;
    }
}
