package com.order_amqp.rabbitmqorder.rpc;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.Charset;

/**
 * 服务端消息的消费
 * @author: lph
 * @date:  2019/7/23 11:10
 * @version V1.0
 */
public class RpcReceive {

    public static final Logger log = LoggerFactory.getLogger(RpcReceive.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues=RabbitMQConfig.TOPIC_QUEUE1)
    public void receiveTopic1(Message msg) {
        // TODO 对消费者中的msg进行数据的解析获取生产者传递过来的接口名称信息然后分别进行相应接口的调用
        log.info("队列1:"+msg.toString());
        String msgBody = new String(msg.getBody());
        //数据处理，返回的Message
        Message repMsg = con(msgBody+"返回了", msg.getMessageProperties().getCorrelationId());

        rabbitTemplate.send(RabbitMQConfig.TOPIC_EXCHANGE, RabbitMQConfig.TOPIC_QUEUE2, repMsg);

    }

    public Message con(String s, byte[] id) {
        MessageProperties mp = new MessageProperties();
        byte[] src = s.getBytes(Charset.forName("UTF-8"));
        mp.setContentType("application/json");
        mp.setContentEncoding("UTF-8");
        mp.setCorrelationId(id);

        return new Message(src, mp);
    }
}
