package com.dida.nowcoder.controller;

import com.dida.nowcoder.annotation.LoginRequired;
import com.dida.nowcoder.entity.Event;
import com.dida.nowcoder.entity.User;
import com.dida.nowcoder.event.EventConsumer;
import com.dida.nowcoder.event.EventProducer;
import com.dida.nowcoder.service.LikeService;
import com.dida.nowcoder.utils.CommunityConstant;
import com.dida.nowcoder.utils.CommunityUtil;
import com.dida.nowcoder.utils.HostHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LikeController implements CommunityConstant {

    @Resource
    private LikeService likeService;

    @Resource
    private HostHolder hostHolder;

    @Resource
    private EventProducer eventProducer;

    @Resource
    private EventConsumer eventConsumer;

    /**
     * 点赞
     * @param entityType
     * @param entityId
     * @param entityUserId
     * @return
     */
    @PostMapping("/like")
    @ResponseBody
    public String like(int entityType, int entityId, int entityUserId, int postId) {
        User user = hostHolder.getUser();
        if (user == null) {
            return CommunityUtil.getJSONString(1);
        }

        //点赞
        likeService.like(user.getId(), entityType, entityId, entityUserId);
        //数量
        long likeCount = likeService.getEntityLikeCount(entityType, entityId);
        //状态
        int likeStatus = likeService.getEntityLikeStatus(user.getId(), entityType, entityId);
        //返回的结果
        Map<String, Object> map = new HashMap<>();
        map.put("likeCount", likeCount);
        map.put("likeStatus", likeStatus);

        // 触发点赞事件
        if (likeStatus == 1) {
            Event event = new Event()
                    .setTopic(TOPIC_LIKE)
                    .setUserId(hostHolder.getUser().getId())
                    .setEntityType(entityType)
                    .setEntityId(entityId)
                    .setEntityUserId(entityUserId)
                    .setData("postId", postId);
            eventProducer.fireEvent(event);
        }

        return CommunityUtil.getJSONString(0, null, map);
    }
}
