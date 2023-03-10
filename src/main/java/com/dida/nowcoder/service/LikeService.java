package com.dida.nowcoder.service;

public interface LikeService {

    //点赞
    public void like(int userId, int entityType, int entityId);

    //查询点赞数量
    public long getEntityLikeCount(int entityType, int entityId);

    //查询某人对某实体的点赞状态
    public int getEntityLikeStatus(int userId, int entityType, int entityId);
}
