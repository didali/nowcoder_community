package com.dida.nowcoder.controller;

import com.dida.nowcoder.entity.Comment;
import com.dida.nowcoder.entity.DiscussPost;
import com.dida.nowcoder.entity.Page;
import com.dida.nowcoder.entity.User;
import com.dida.nowcoder.service.CommentService;
import com.dida.nowcoder.service.DiscussPostService;
import com.dida.nowcoder.service.UserService;
import com.dida.nowcoder.utils.CommunityConstant;
import com.dida.nowcoder.utils.CommunityUtil;
import com.dida.nowcoder.utils.HostHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@Controller
@RequestMapping("/discuss")
public class DiscussPostController implements CommunityConstant {

    private static final Logger logger = LoggerFactory.getLogger(DiscussPostController.class);

    @Resource
    private DiscussPostService discussPostService;

    @Resource
    private HostHolder hostHolder;

    @Resource
    private UserService userService;

    @Resource
    private CommentService commentService;

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
    public String getDiscussPostById(@PathVariable("discussPostId") int discussPostId, Model model, Page page) {
        //帖子
        DiscussPost discussPost = discussPostService.getDiscussPostById(discussPostId);
        model.addAttribute("post", discussPost);

        //作者
        User user = userService.getUserById(discussPost.getUserId());
        model.addAttribute("user", user);

        //评论分页信息
        page.setLimit(5);
        page.setPath("/discuss/detail/" + discussPostId);
        page.setRows(discussPost.getCommentCount());

        //评论列表
        List<Comment> commentList = commentService.getCommentsByEntity(
                ENTITY_TYPE_POST, discussPost.getId(), page.getOffset(), page.getLimit());
        //评论的VO列表
        List<Map<String, Object>> commentVoList = new ArrayList<>();

        if (commentList != null) {
            for (Comment comment : commentList) {
                //评论的VO
                Map<String, Object> commentVo = new HashMap<>();
                //评论
                commentVo.put("comment", comment);
                //评论的编辑者
                commentVo.put("user", userService.getUserById(comment.getUserId()));

                //回复列表
                List<Comment> replyList = commentService.getCommentsByEntity(
                        ENTITY_TYPE_COMMENT, comment.getId(), 0, Integer.MAX_VALUE
                );
                //回复的VO列表
                List<Map<String, Object>> replyVoList = new ArrayList<>();

                if (replyList != null) {
                    for (Comment reply : replyList) {
                        Map<String, Object> replyVo = new HashMap<>();
                        //回复
                        replyVo.put("reply", reply);
                        //回复的编辑者
                        replyVo.put("user", userService.getUserById(reply.getUserId()));
                        //回复目标
                        User target = reply.getTargetId() == 0 ? null : userService.getUserById(reply.getTargetId());
                        replyVo.put("target", target);

                        replyVoList.add(replyVo);
                    }
                }
                commentVo.put("replyList", replyVoList);

                //回复数目
                int replyCount = commentService.getCountByEntity(ENTITY_TYPE_COMMENT, comment.getId());
                commentVo.put("replyCount", replyCount);

                commentVoList.add(commentVo);
            }
        }
        model.addAttribute("comments", commentVoList);

        return "/site/discuss-detail";
    }
}
