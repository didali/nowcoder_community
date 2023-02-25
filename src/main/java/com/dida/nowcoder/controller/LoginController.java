package com.dida.nowcoder.controller;

import com.dida.nowcoder.entity.User;
import com.dida.nowcoder.service.UserService;
import com.dida.nowcoder.utils.CommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class LoginController implements CommunityConstant {

    @Autowired
    private UserService userService;

    //访问注册页面
    @GetMapping("/register")
    public String getRegisterPage() {
        return "/site/register";
    }

    //访问登录页面
    @GetMapping("/login")
    public String getLoginPage() {
        return "/site/login";
    }


    /**
     * 注册功能
     * @param model 模型
     * @param user 注册用户
     * @return 返回
     */
    @PostMapping("/register")
    public String register(Model model, User user) {
        Map<String, Object> map = userService.register(user);

        if (map == null || map.isEmpty()) {
            model.addAttribute("msg", "注册成功，我们已经向你的邮箱发送了一封激活邮件，请点击激活");
            //跳转路径
            model.addAttribute("target", "/index");
            return "/site/operate-result";
        } else {
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            model.addAttribute("emailMsg", map.get("emailMsg"));
            return "/site/register";
        }
    }

    /**
     * 对注册账号进行激活
     * @param model 模型
     * @param userId 用户id
     * @param code 激活码
     * @return
     */
    @GetMapping("/activation/{userId}/{code}")
    public String activation(Model model, @PathVariable("userId") int userId, @PathVariable("code") String code) {
        int result = userService.activation(userId, code);

        if (result == ACTIVATION_SUCCESS) {
            model.addAttribute("msg", "激活成功，你的账号已经可以正常使用了！");
            //跳转路径
            model.addAttribute("target", "/login");
        } else if (result == ACTIVATION_REPEAT) {
            model.addAttribute("msg", "该账号已经激活过了！");
            model.addAttribute("target", "/index");
        } else {
            model.addAttribute("msg", "激活失败，你提供的激活码有误！");
            model.addAttribute("target", "/index");
        }
        return "/site/operate-result";
    }
}
