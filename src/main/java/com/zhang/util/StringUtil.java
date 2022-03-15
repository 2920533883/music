package com.zhang.util;

/**
 * String工具类
 */
public class StringUtil {
    /**
     * 定义下划线
     */
    private static final char UNDERLINE = '_';

    /**
     * String为空判断(不允许空格)
     * @param str
     * @return boolean
     */
    public static boolean isBlank(String str) {
        return str == null || "".equals(str.trim());
    }

    /**
     * String不为空判断(不允许空格)
     * @param str
     * @return boolean
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * Byte数组为空判断
     * @param bytes
     * @return boolean
     */
    public static boolean isNull(byte[] bytes) {
        // 根据byte数组长度为0判断
        return bytes == null || bytes.length == 0;
    }

    /**
     * Byte数组不为空判断
     * @param bytes
     * @return boolean
     */
    public static boolean isNotNull(byte[] bytes) {
        return !isNull(bytes);
    }
}

