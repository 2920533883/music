package com.zhang.bean;

/**
 * 权限认证实体
 */
public class AuthConstant {

    /**
     * cookie中存储的token字段名
     */
    public final static String COOKIE_TOKEN_NAME = "U-Token";

    /**
     * token有效时间 时*分*秒*1000L
     */
    public final static Long EXPIRE_TIME = 24 * 60 * 60 * 1000L; // 24小时

    //登录认证结果,返回给前端
    public final static String SUCCESS = "操作成功！";

    public final static String UNKNOWN_ACCOUNT = "用户不存在。";

    public final static String ALREADY_REG = "此用户名已被注册！";

    public final static String WRONG_PASSWORD = "密码错误。";

    public final static String TOKEN_BLANK = "验证失败，token为空，请登录。";

    public final static String TOKEN_INVALID = "验证失败，token错误。";

    public final static String TOKEN_EXPIRE = "验证失败，token过期，请重新登录。";

    public final static String AUTHENTICATE_FAIL = "无访问权限，请尝试登录或联系管理员。";

}

