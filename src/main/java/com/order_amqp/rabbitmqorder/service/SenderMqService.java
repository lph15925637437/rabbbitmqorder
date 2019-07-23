package com.order_amqp.rabbitmqorder.service;

import com.order_amqp.rabbitmqorder.common.RabbitMqEnum;
import com.order_amqp.rabbitmqorder.component.RabbitmqSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  将抢单的手机号信息通过rabbitmq存放到队列，等待处理
 */
@Service
public class SenderMqService {
    private static final Logger logger = LoggerFactory.getLogger(SenderMqService.class);

    @Autowired
    private RabbitmqSender rabbitmqSender;

    public void sendRobbingMsg(String phone){
        try {
            // 对手机号码进行校验
            boolean result=phone.matches("[0-9]+");
            if (!result) {
                logger.error("抢单手机号码格式不正确,抢单失败:msg={}", phone);
                return;
            }

            // 进入队列
            rabbitmqSender.sendUserOderMsg2Queue(RabbitMqEnum.ExchangeEnum.DIRECT_EXCHANGE.getCode(),
                    RabbitMqEnum.RoutingEnum.USER_ORDER_ROUTING.getCode(), phone);
        } catch (Exception e) {
            logger.error("发送抢单信息入队列发生异常: phone={}", phone);
        }
    }
}
