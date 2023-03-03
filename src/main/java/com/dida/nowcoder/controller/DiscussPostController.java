package com.dida.nowcoder.controller;

import com.dida.nowcoder.entity.DiscussPost;
import com.dida.nowcoder.entity.User;
import com.dida.nowcoder.service.DiscussPostService;
import com.dida.nowcoder.service.UserService;
import com.dida.nowcoder.utils.CommunityUtil;
import com.dida.nowcoder.utils.HostHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@Controller
@RequestMapping("/discuss")
public class DiscussPostController {

    private static final Logger logger = LoggerFactory.getLogger(DiscussPostController.class);

    @Resource
    private DiscussPostService discussPostService;

    @Resource
    private HostHolder hostHolder;

    @Resource
    private UserService userService;

    /**
     * 发布帖子
     * @param title 帖子标题
     * @param content 帖子内容
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public String addDiscussPost(String title, String content) {
        User user = hostHolder.getUser();

        if (user == null) {
            return CommunityUtil.getJSONString(403, "你还没有登录！");
        }

        DiscussPost discussPost = new DiscussPost();

        discussPost.setUserId(user.getId());
        discussPost.setTitle(title);
        discussPost.setContent(content);
        discussPost.setCreateTime(new Date());
        discussPostService.addDiscussPost(discussPost);

        return CommunityUtil.getJSONString(0, "发布成功！");
    }

    /**
     * 查询一个帖子的信息以及内容
     *
     * @param discussPostId 帖子编号
     * @param model 模型
     * @return
     */
    @GetMapping("/detail/{discussPostId}")
    public String getDiscussPostByid(@PathVariable("discussPostId") int discussPostId, Model model) {
        logger.debug("{}", discussPostId);


        //帖子
        DiscussPost discussPost = discussPostService.getDiscussPostById(discussPostId);
        model.addAttribute("post", discussPost);

        //作者
        User user = userService.getUserById(discussPost.getUserId());
        model.addAttribute("user", user);

        return "/site/discuss-detail";
    }
}
