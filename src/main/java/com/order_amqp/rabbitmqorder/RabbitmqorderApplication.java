package com.order_amqp.rabbitmqorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RabbitmqorderApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqorderApplication.class, args);
    }
}
