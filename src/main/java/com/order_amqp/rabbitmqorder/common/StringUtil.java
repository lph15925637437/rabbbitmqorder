package com.order_amqp.rabbitmqorder.common;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil extends StringUtils {

    public static final String fillLeft(String source, char fill, int len)
    {
        StringBuffer ret = new StringBuffer();
        if (null == source)
            ret.append("");
        if (source.length() > len) {
            ret.append(source);
        } else {
            int slen = source.length();
            while (ret.toString().length() + slen < len) {
                ret.append(fill);
            }
            ret.append(source);
        }
        return ret.toString();
    }

    public static final String filRight(String source, char fill, int len)
    {
        StringBuffer ret = new StringBuffer();
        if (null == source)
            ret.append("");
        if (source.length() > len) {
            ret.append(source);
        } else {
            ret.append(source);
            while (ret.toString().length() < len) {
                ret.append(fill);
            }
        }
        return ret.toString();
    }

    public static boolean isEmail(String email)
    {
        Pattern pattern = Pattern.compile("^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$");

        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isMobileNO(String phoneNo)
    {
        Pattern p = Pattern.compile("^1[3|4|5|7|8]\\d{9}$");
        Matcher m = p.matcher(phoneNo);
        return m.matches();
    }

    public static final boolean isIp(String ip)
    {
        Pattern p = Pattern.compile("^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$");

        Matcher m = p.matcher(ip);
        return m.matches();
    }

    public static final boolean isPostCode(String postCode)
    {
        Pattern p = Pattern.compile("[1-9]\\d{5}(?!\\d)");
        Matcher m = p.matcher(postCode);
        return m.matches();
    }


    public static final boolean isPassport(String passPort)
    {
        Pattern p1 = Pattern.compile("^[a-zA-Z]{5,17}$");
        Pattern p2 = Pattern.compile("^[a-zA-Z0-9]{5,17}$");
        Matcher m1 = p1.matcher(passPort);
        Matcher m2 = p2.matcher(passPort);
        return (m1.matches()) || (m2.matches());
    }

    public static final boolean isDate(String dateStr)
    {
        if (isBlank(dateStr)) {
            return false;
        }
        Pattern p = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-/]{1}((((0?[13578])|(1[02]))[\\-/]{1}((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-/]{1}((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-/]{1}((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-/]{1}((((0?[13578])|(1[02]))[\\-/]{1}((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-/]{1}((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-/]{1}((0?[1-9])|(1[0-9])|(2[0-8]))))))");

        Matcher m = p.matcher(dateStr);

        boolean flag = false;
        if ((dateStr.split("-").length == 3) || (dateStr.split("/").length == 3)) {
            flag = true;
        }
        return (m.matches()) && (flag);
    }

    public static final int getLength(String str)
    {
        int length = 0;

        if (isNotBlank(str)) {
            char[] chars = str.toCharArray();
            for (int i = 0; i < chars.length; i++)
            {
                char temp = chars[i];

                if (isChinese(String.valueOf(temp)))
                {
                    length += 2;
                }
                else {
                    length++;
                }
            }
        }
        return length;
    }

    public static final boolean isChinese(String str)
    {
        char[] ch = str.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

        return (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) || (ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS) || (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) || (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B) || (ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION) || (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) || (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION);
    }

    /**
     * 检查指定的字符串是否为空。
     * <ul>
     * <li>SysUtils.isEmpty(null) = true</li>
     * <li>SysUtils.isEmpty("") = true</li>
     * <li>SysUtils.isEmpty("   ") = true</li>
     * <li>SysUtils.isEmpty("abc") = false</li>
     * </ul>
     *
     * @param value 待检查的字符串
     * @return true/false
     */
    public static boolean isEmpty(String value) {
        int strLen;
        if (value == null || (strLen = value.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(value.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查对象是否为数字型字符串,包含负数开头的。
     */
    public static boolean isNumeric(Object obj) {
        if (obj == null) {
            return false;
        }
        char[] chars = obj.toString().toCharArray();
        int length = chars.length;
        if(length < 1)
            return false;

        int i = 0;
        if(length > 1 && chars[0] == '-')
            i = 1;

        for (; i < length; i++) {
            if (!Character.isDigit(chars[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查指定的字符串列表是否不为空。
     */
    public static boolean areNotEmpty(String... values) {
        boolean result = true;
        if (values == null || values.length == 0) {
            result = false;
        } else {
            for (String value : values) {
                result &= !isEmpty(value);
            }
        }
        return result;
    }

    /**
     * 把通用字符编码的字符串转化为汉字编码。
     */
    public static String unicodeToChinese(String unicode) {
        StringBuilder out = new StringBuilder();
        if (!isEmpty(unicode)) {
            for (int i = 0; i < unicode.length(); i++) {
                out.append(unicode.charAt(i));
            }
        }
        return out.toString();
    }

    /**
     * 过滤不可见字符
     */
    public static String stripNonValidXMLCharacters(String input) {
        if (input == null || ("".equals(input)))
            return "";
        StringBuilder out = new StringBuilder();
        char current;
        for (int i = 0; i < input.length(); i++) {
            current = input.charAt(i);
            if ((current == 0x9) || (current == 0xA) || (current == 0xD)
                    || ((current >= 0x20) && (current <= 0xD7FF))
                    || ((current >= 0xE000) && (current <= 0xFFFD))
                    || ((current >= 0x10000) && (current <= 0x10FFFF)))
                out.append(current);
        }
        return out.toString();
    }

    /**
     * 获取当前时间戳，单位秒
     * @return
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis()/1000;
    }

    /**
     * 获取当前时间戳，单位毫秒
     * @return
     */
    public static long getCurrentTimestampMs() {
        return System.currentTimeMillis();
    }

    /**
     * 生成 uuid， 即用来标识一笔单，也用做 nonce_str
     * @return
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
    }
}

