package com.dida.nowcoder.utils;

public class RedisKeyUtil {

    //key的单词分隔符
    private static final String SPLIT = ":";
    //实体的赞的前缀
    private static final String PREFIX_ENTITY_LIKE = "like:entity";

    //某个实体的赞
    public static String getEntityLike(int entityType, int entityId) {
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }
}
