package com.order_amqp.rabbitmqorder.exceptions;

public enum ServiceErrorEnum {
    E_RedisService_Error("S10002","Redis服务出错了!");


    private String errorCode;

    private String message;

    ServiceErrorEnum(String code,String message){
        this.errorCode=code;
        this.message=message;
    }

    public String getMessage(){
        return"[code="+errorCode+",message="+message+"]";
    }
    public String getErrorCode() {

        return errorCode;
    }

    public String getMsg(){
        return message;
    }
}
