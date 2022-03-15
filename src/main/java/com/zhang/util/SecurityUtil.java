package com.zhang.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * 密码加密工具类
 */
public class SecurityUtil {

    /**
     * 生成SHA-256散列值
     * @param str the String of encrypting
     * @return the SHA-256 of String
     */
    public static String getSHA256(String str) {
        String encodingStr = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            encodingStr = byte2Hex(messageDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodingStr;
    }

    /**
     * 将字节数组转化为字符串
     * @param bytes byte arrays
     * @return the String of byte arrays
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuilder stringBuffer = new StringBuilder();
        String temp;
        for (byte aByte : bytes) {
            temp = Integer.toHexString(aByte & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}
