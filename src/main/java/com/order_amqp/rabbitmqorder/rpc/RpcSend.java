package com.order_amqp.rabbitmqorder.rpc;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.Charset;

/**
 * 发送消息并同步等待返回值
 *
 * @version V1.0
 * @author: lph
 * @date: 2019/7/23 10:59
 */
public class RpcSend {

    public static final Logger log = LoggerFactory.getLogger(RpcSend.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void rpcSendMethod() {
        //报文body
        String sss = "报文的内容";
//封装Message
        Message msg = this.con(sss);
        log.info("客户端--------------------" + msg.toString());
//使用sendAndReceive方法完成rpc调用
        Message message = rabbitTemplate.sendAndReceive(RabbitMQConfig.TOPIC_EXCHANGE, RabbitMQConfig.TOPIC_QUEUE1, msg);
//提取rpc回应内容body
        String response = new String(message.getBody());
        log.info("回应：" + response);
        log.info("rpc完成---------------------------------------------");
    }

    public Message con(String s) {
        MessageProperties mp = new MessageProperties();
        // TODO 在发送消息的时候可以将要调用的服务端类和接口名称一起带过去，在服务端进行该信息的解析并进行相应的方法的调用参数可以封装成map的类型进行数据的传递
        byte[] src = s.getBytes(Charset.forName("UTF-8"));
        //mp.setReplyTo("adsdas");   加载AmqpTemplate时设置，这里设置没用
        //mp.setCorrelationId("2222");   系统生成，这里设置没用

        mp.setContentType("application/json");
        mp.setContentEncoding("UTF-8");
        mp.setContentLength((long) s.length());
        return new Message(src, mp);
    }
}
