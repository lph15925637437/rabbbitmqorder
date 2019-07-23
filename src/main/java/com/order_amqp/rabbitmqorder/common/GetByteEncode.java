package com.order_amqp.rabbitmqorder.common;

import org.mozilla.universalchardet.UniversalDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取文件编码类型
 *
 * @author XSL
 * @version Id: GetByteEncode.java, V 1.0 2018/11/30 10:03 XSL Exp $$
 */
public class GetByteEncode {

    private static final Logger log = LoggerFactory.getLogger(GetByteEncode.class);

    /**
     * 获取文件编码类型
     *
     * @param bytes 文件bytes数组
     * @return      编码类型
     */
    public static String getEncoding(byte[] bytes) {
        String defaultEncoding = "UTF-8";
        UniversalDetector detector = new UniversalDetector(null);
        detector.handleData(bytes, 0, bytes.length);
        detector.dataEnd();
        String encoding = detector.getDetectedCharset();
        detector.reset();
        log.info("字符编码是：{}", encoding);
        if (encoding == null) {
            encoding = defaultEncoding;
        }
        return encoding;
    }
}
