package com.dida.nowcoder.service.impl;

import com.dida.nowcoder.service.LikeService;
import com.dida.nowcoder.utils.RedisKeyUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LikeServiceImpl implements LikeService {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 点赞功能
     *
     * @param userId 登录用户id（点赞人userId）
     * @param entityType 点赞的帖子(评论)实体类型
     * @param entityId 点赞的帖子(评论)的id
     * @param entityUserId 帖子(评论)发布用户的userId（被赞人的userId）
     */
    @Override
    public void like(int userId, int entityType, int entityId, int entityUserId) {
/*        //组合需要存取的数据的key
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);

        //判断是否点过赞
        boolean isMember = redisTemplate.opsForSet().isMember(entityLikeKey, userId);
        if (isMember) {
            //点过赞的则取消点赞
            redisTemplate.opsForSet().remove(entityLikeKey, userId);
        } else {
            redisTemplate.opsForSet().add(entityLikeKey, userId);
        }*/

        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
                String userLikeKey = RedisKeyUtil.getUserLikeKey(entityUserId);

                //查看当前用户有没有对实体点过赞
                boolean isMember = operations.opsForSet().isMember(entityLikeKey, userId);

                //开启事务
                operations.multi();

                if (isMember) {
                    //点过赞的则取消点赞，同时用户赞减1
                    operations.opsForSet().remove(entityLikeKey, userId);
                    operations.opsForValue().decrement(userLikeKey);
                } else {
                    operations.opsForSet().add(entityLikeKey, userId);
                    operations.opsForValue().increment(userLikeKey);
                }

                return operations.exec();
            }
        });
    }

    @Override
    public long getEntityLikeCount(int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);

        //返回统计的数量
        return redisTemplate.opsForSet().size(entityLikeKey);
    }

    @Override
    public int getEntityLikeStatus(int userId, int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);

        //返回点赞状态
        return redisTemplate.opsForSet().isMember(entityLikeKey, userId) ? 1 : 0;
    }

    @Override
    public int getUserLikeCount(int userId) {
        String userLikeKey = RedisKeyUtil.getUserLikeKey(userId);
        Integer count = (Integer) redisTemplate.opsForValue().get(userLikeKey);
        return count == null ? 0 : count.intValue();
    }
}
