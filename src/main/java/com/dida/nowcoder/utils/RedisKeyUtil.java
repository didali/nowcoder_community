package com.dida.nowcoder.utils;

public class RedisKeyUtil {

    //key的单词分隔符
    private static final String SPLIT = ":";
    //实体的赞的前缀
    private static final String PREFIX_ENTITY_LIKE = "like:entity";
    //用户收到的赞的前缀
    private static final String PREFIX_USER_LIKE = "like:user";
    //关注的目标的前缀
    private static final String PREFIX_FOLLOWEE = "followee";
    //关注者（粉丝）的前缀
    private static final String PREFIX_FOLLOWER = "follower";
    //验证码的前缀
    private static final String PREFIX_KAPTCHA = "kaptcha";
    //登录凭证的前缀
    private static final String PREFIX_TICKET = "ticket";
    //用户的前缀
    private static final String PREFIX_USER = "ticket";

    //某个实体的赞
    public static String getEntityLikeKey(int entityType, int entityId) {
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }

    //某个用户的赞
    public static String getUserLikeKey(int userId) {
        return PREFIX_USER_LIKE + SPLIT + userId;
    }

    //某个用户关注的实体
    public static String getFolloweeKey(int userId, int entityType) {
        return PREFIX_FOLLOWEE + SPLIT + userId + SPLIT + entityType;
    }

    //某个实体拥有的粉丝
    public static String getFollowerKey(int entityType, int entityId) {
        return PREFIX_FOLLOWER + SPLIT + entityType + SPLIT + entityId;
    }

    //获取验证码的key
    public static String getKaptchaKey(String owner) {
        return PREFIX_KAPTCHA + SPLIT + owner;
    }

    //获取登录的凭证的key
    public static String getTicketKey(String ticket) {
        return PREFIX_TICKET + SPLIT + ticket;
    }

    //用户的key
    public static String getUserKey(int userId) {
        return PREFIX_USER + SPLIT + userId;
    }
}
