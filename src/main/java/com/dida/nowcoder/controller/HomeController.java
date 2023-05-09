package com.dida.nowcoder.controller;

import com.dida.nowcoder.entity.DiscussPost;
import com.dida.nowcoder.entity.Page;
import com.dida.nowcoder.entity.User;
import com.dida.nowcoder.service.DiscussPostService;
import com.dida.nowcoder.service.LikeService;
import com.dida.nowcoder.service.UserService;
import com.dida.nowcoder.utils.CommunityConstant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController implements CommunityConstant {

    @Resource
    private UserService userService;

    @Resource
    private DiscussPostService discussPostService;

    @Resource
    private LikeService likeService;

    /**
     * 获取页面评论
     * @param model 模型
     * @param page 分页
     * 1
     */
    @GetMapping("/index")
    public String getIndexPage(Model model, Page page) {
        /*
            方法调用之前，springMVC会自动实例化Model和Page，并将Page注入给Model
            所以，在thymeleaf中可以直接访问Page对象当中的数据
         */
        page.setRows(discussPostService.getDiscussPostsRows(0));
        page.setPath("/index");

        List<DiscussPost> list = discussPostService.getDiscussPosts(0, page.getOffset(), page.getLimit());
        List<Map<String, Object>> discussPosts = new ArrayList<>();

        if (list != null) {
            for (DiscussPost post : list) {
                Map<String, Object> map = new HashMap<>();
                User user = userService.getUserById(post.getUserId());

                map.put("post", post);
                map.put("user", user);

                //查询帖子获赞情况
                long likeCount = likeService.getEntityLikeCount(ENTITY_TYPE_POST, post.getId());
                map.put("likeCount", likeCount);

                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts", discussPosts);
        return "/index";
    }

    /**
     * 获取错误页面
     *
     * @return
     */
    @GetMapping("/error")
    public String getErrorPage() {
        return "/error/500";
    }
}
