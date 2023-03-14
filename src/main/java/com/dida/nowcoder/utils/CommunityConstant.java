package com.dida.nowcoder.utils;

//记录激活状态
public interface CommunityConstant {

    //激活成功
    int ACTIVATION_SUCCESS = 0;

    //重复激活
    int ACTIVATION_REPEAT = 1;

    //激活失败
    int ACTIVATION_FAILURE = 2;

    //默认状态下登录凭证超时时间
    int DEFAULT_EXPIRED_SECONDS = 3600 * 24;

    //记住状态下登录凭证超时时间
    int REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 30;

    //帖子实体类型
    int ENTITY_TYPE_POST = 1;

    //评论实体类型
    int ENTITY_TYPE_COMMENT = 2;

    //用户实体类型
    int ENTITY_TYPE_USER = 3;

    //主题：评论
    String TOPIC_COMMENT = "comment";

    //主题：点赞
    String TOPIC_LIKE = "like";

    //主题：关注
    String TOPIC_FOLLOW = "follow";

    //系统用户id
    int SYSTEM_USER_ID = 1;
}
