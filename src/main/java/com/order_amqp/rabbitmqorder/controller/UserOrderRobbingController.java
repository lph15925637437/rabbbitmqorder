package com.order_amqp.rabbitmqorder.controller;

import com.order_amqp.rabbitmqorder.service.InitService;
import com.order_amqp.rabbitmqorder.service.InitService2UserOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模拟用户抢单入口
 */
@RestController
public class UserOrderRobbingController {
    private static final Logger logger = LoggerFactory.getLogger(UserOrderRobbingController.class);

    @Autowired
    private InitService initService;

    @Autowired
    private InitService2UserOrder initService2UserOrder;

    @Value("${reuqest.num}")
    private int requestNum;

    // 随机的手机号码
    public static int phone = 0;

    /**
     *  初始化并发数据
     * @return
     */
    @RequestMapping("/robbing/thread")
    public String robbingThread() {
        // initService.generateMultiThread();
        for (int i = 0; i < requestNum; i++) {
            phone += 1;
            initService2UserOrder.initConcurrentNum(String.valueOf(phone));
        }
        return "success";
    }
}
