package com.dida.nowcoder.service;

import java.util.List;
import java.util.Map;

public interface FollowService {

    //关注
    public void follow(int userId, int entityType, int entityId);

    //取消关注
    public void unfollow(int userId, int entityType, int entityId);

    //获取关注数
    public long getFolloweeCount(int userId, int entityType);

    //获取粉丝数
    public long getFollowerCount(int entityType, int entityId);

    //查询当前用户是否已关注该实体
    public boolean hasFollowed(int userId, int entityType, int entityId);

    //查询某用户关注的人
    public List<Map<String, Object>> getFolloweeList(int userId, int offset, int limit);

    //查询某用户的粉丝
    public List<Map<String, Object>> getFollowerList(int userId, int offset, int limit);
}
