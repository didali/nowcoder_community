package com.dida.nowcoder.controller;

import com.dida.nowcoder.annotation.LoginRequired;
import com.dida.nowcoder.entity.User;
import com.dida.nowcoder.service.FollowService;
import com.dida.nowcoder.service.LikeService;
import com.dida.nowcoder.service.UserService;
import com.dida.nowcoder.utils.CommunityConstant;
import com.dida.nowcoder.utils.CommunityUtil;
import com.dida.nowcoder.utils.HostHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/user")
public class UserController implements CommunityConstant {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${community.path.upload}")
    private String uploadPath;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Resource
    private UserService userService;

    @Resource
    private HostHolder hostHolder;

    @Resource
    private LikeService likeService;

    @Resource
    private FollowService followService;


    /**
     * 账号设置页面接口
     *
     * @return 返回账号设置页面
     */
    @LoginRequired
    @GetMapping("/setting")
    public String getUserSettingPage() {
        return "/site/setting";
    }

    /**
     * 上传头像
     *
     * @param headerImage 用户头像路径
     * @param model       模型
     * @return
     */
    @LoginRequired
    @PostMapping("/upload")
    public String uploadHeader(MultipartFile headerImage, Model model) {
        if (headerImage == null) {
            model.addAttribute("error", "你还没有选择图片!");
            return "/site/setting";
        }

        String fileName = headerImage.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)) {
            model.addAttribute("error", "文件的格式不正确！");
            return "/site/setting";
        }

        //生成随机文件名
        fileName = CommunityUtil.generateUUID() + suffix;
        //确定文件的存储路径
        File dest = new File(uploadPath + "/" + fileName);
        try {
            headerImage.transferTo(dest);
        } catch (IOException e) {
            logger.error("上传文件失败：" + e.getMessage());
            throw new RuntimeException("上传文件失败，服务器发生异常：" + e);
        }

        User user = hostHolder.getUser();
        String headerUrl = domain + contextPath + "/user/header/" + fileName;
        userService.updateHeader(user.getId(), headerUrl);

        return "redirect:/index";
    }

    /**
     * 获取用户头像
     * @param fileName 头像文件名
     * @param response
     */
    @GetMapping("/header/{fileName}")
    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        //服务器存放路径
        fileName = uploadPath + "/" + fileName;
        //文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //响应图片
        response.setContentType("image/" + suffix);

        try (
                /**
                 * Java7的语法，这样写的作用是：
                 *     在这里面声明的变量，在编译的时候，会为try...cache加上finally
                 *     同时，在finally中会调用变量的close方法
                 *     前提：在这里面声明的变量有close方法
                 */
                FileInputStream fis = new FileInputStream(fileName);
                OutputStream os = response.getOutputStream();
        ) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1) {
                os.write(buffer, 0, b);
            }
        } catch (IOException e) {
            logger.error("读取头像失败：" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 用户个人信息页面
     *
     * @param userId 用户id
     * @param model 模板
     * @return
     */
    @GetMapping("/profile/{userId}")
    public String getProfilePage(@PathVariable("userId") int userId, Model model) {
        //查询需要访问的用户
        User user = userService.getUserById(userId);
        //判断userId是否存在，不存在则抛出异常
        if (user == null) {
            throw new RuntimeException("该用户不存在！");
        }
        //用户
        model.addAttribute("user", user);

        //查询获赞数量
        int likeCount = likeService.getUserLikeCount(userId);
        model.addAttribute("likeCount", likeCount);

        //查询关注数量
        long followeeCount = followService.getFolloweeCount(userId, ENTITY_TYPE_USER);
        model.addAttribute("followeeCount", followeeCount);

        //查询粉丝数
        long followerCount = followService.getFollowerCount(ENTITY_TYPE_USER, userId);
        model.addAttribute("followerCount", followerCount);

        //是否已关注
        boolean hasFollowed = false;
        if (hostHolder.getUser() != null) {
            hasFollowed = followService.hasFollowed(hostHolder.getUser().getId(), ENTITY_TYPE_USER, userId);
        }
        model.addAttribute("hasFollowed", hasFollowed);

        return "/site/profile";
    }

    /**
     * 修改密码
     * @return 修改成功之后返回登录页面重新登录
     */
    @PostMapping("/updatePassword")
    public String updatePassword(String oldPassword, String newPassword, String confirmPassword, Model model) {
/*        User user = hostHolder.getUser();

        if (!oldPassword.equals(user.getPassword())) {
            model.addAttribute("oldPasswordMsg", "原密码有误！");
            return "redirect:/user/setting";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("confirmPassword", "两次输入的新密码不一致");
            return "redirect:/user/setting";
        }

        //修改密码
        userService.updatePassword(user.getId(), newPassword);
        //修改之后退出登录并且返回登录页面*/

        return "redirect:/user/logout";
    }
}
