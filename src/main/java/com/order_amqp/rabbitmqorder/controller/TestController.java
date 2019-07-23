package com.order_amqp.rabbitmqorder.controller;

import com.order_amqp.rabbitmqorder.common.Utils;
import com.order_amqp.rabbitmqorder.domain.Person;
import com.order_amqp.rabbitmqorder.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TestService testService;

    @RequestMapping("/test")
    public String test(){
        Person person = new Person("刘沛汉", "15925637437", 25);
        try {
            // 对象转换成bytes
            byte[] bytes = Utils.object2Byte(person);
            person = Utils.byte2Object(bytes, Person.class);
        } catch (java.io.IOException e) {
            logger.error("object to byte error={}", e.getMessage());
            return "error";
        }
        return person.toString();
    }

    @RequestMapping("init")
    public String init(){
        int count = testService.test();
        return count + "";
    }
}
