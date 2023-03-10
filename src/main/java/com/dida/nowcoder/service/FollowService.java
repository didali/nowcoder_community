package com.dida.nowcoder.service;

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
}
