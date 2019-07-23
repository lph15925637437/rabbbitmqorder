package com.order_amqp.rabbitmqorder.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 *  对象间的转换
 */
public class Utils {
    private static final Logger logger = LoggerFactory.getLogger(Utils.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static byte[] object2Byte(Object o) throws JsonProcessingException {
        String value = objectMapper.writeValueAsString(o);
        return value.getBytes();
    }

    public static <T> T byte2Object(byte[] bytes, Class clazz) throws IOException {

        if (bytes.length <= 0) {
            logger.warn("bytes is no exist={}",bytes);
            return null;
        }
        return (T) objectMapper.readValue(bytes, clazz);
    }
}
