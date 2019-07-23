package com.order_amqp.rabbitmqorder.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 *  rabbitmq发送消息工具类
 */
@Component
public class RabbitmqSender {

    private static final Logger logger = LoggerFactory.getLogger(RabbitmqSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     *  将消息发送到routeKey指定的queue中
     */
    public void  sendUserOderMsg2Queue(String exchangeName, String routeKey, Object obj){
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        logger.info("phone send to user_order_queue={}", correlationData.getId());
        rabbitTemplate.convertSendAndReceive(exchangeName, routeKey, obj, correlationData);
    }

}
