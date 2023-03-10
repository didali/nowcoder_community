package com.dida.nowcoder.service.impl;

import com.dida.nowcoder.service.LikeService;
import com.dida.nowcoder.utils.RedisKeyUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LikeServiceImpl implements LikeService {

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void like(int userId, int entityType, int entityId) {
        //组合需要存取的数据的key
        String entityLikeKey = RedisKeyUtil.getEntityLike(entityType, entityId);

        //判断是否点过赞
        boolean isMember = redisTemplate.opsForSet().isMember(entityLikeKey, userId);
        if (isMember) {
            //点过赞的则取消点赞
            redisTemplate.opsForSet().remove(entityLikeKey, userId);
        } else {
            redisTemplate.opsForSet().add(entityLikeKey, userId);
        }
    }

    @Override
    public long getEntityLikeCount(int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLike(entityType, entityId);

        //返回统计的数量
        return redisTemplate.opsForSet().size(entityLikeKey);
    }

    @Override
    public int getEntityLikeStatus(int userId, int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLike(entityType, entityId);

        //返回点赞状态
        return redisTemplate.opsForSet().isMember(entityLikeKey, userId) ? 1 : 0;
    }
}
