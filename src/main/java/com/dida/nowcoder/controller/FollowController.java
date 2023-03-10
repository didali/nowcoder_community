package com.dida.nowcoder.controller;

import com.dida.nowcoder.annotation.LoginRequired;
import com.dida.nowcoder.entity.User;
import com.dida.nowcoder.service.FollowService;
import com.dida.nowcoder.utils.CommunityUtil;
import com.dida.nowcoder.utils.HostHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class FollowController {

    @Resource
    private FollowService followService;

    @Resource
    private HostHolder hostHolder;

    /**
     * 关注
     *
     * @param entityType 关注的实体的类型
     * @param entityId 关注的实体的id
     * @return 返回
     */
    @PostMapping("/follow")
    @ResponseBody
    public String follow(int entityType, int entityId) {
        User user = hostHolder.getUser();
        if (user == null) {
            return CommunityUtil.getJSONString(1, "用户未登录");
        }

        followService.follow(user.getId(), entityType, entityId);

        return CommunityUtil.getJSONString(0, "已关注！");
    }

    /**
     * 取消关注
     *
     * @param entityType 实体类型
     * @param entityId 实体id
     * @return
     */
    @PostMapping("/unfollow")
    @ResponseBody
    public String unfollow(int entityType, int entityId) {
        User user = hostHolder.getUser();
        if (user == null) {
            return CommunityUtil.getJSONString(1, "用户未登录");
        }
            followService.unfollow(user.getId(), entityType, entityId);

        return CommunityUtil.getJSONString(0, "已取消关注！");
    }


}
