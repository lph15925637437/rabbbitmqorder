package com.order_amqp.rabbitmqorder.common;

/**
 *  rabbitmq枚举类
 */
public class RabbitMqEnum {
    /**
     *  定义交换机
     */
    public enum ExchangeEnum{
        DIRECT_EXCHANGE("user_order_exchange", "用户订单交换机");

        private String code;
        private String name;

        ExchangeEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    /**
     *  定义队列
     */
    public enum QueueEnum{
        USER_ORDER_QUEUE("user_order_queue", "用户订单队列");

        private String code;
        private String name;

        QueueEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    /**
     *  定义路由键
     */
    public enum RoutingEnum{
        USER_ORDER_ROUTING("user_order_routing", "用户订单路由键");

        private String code;
        private String name;

        RoutingEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }
}
