package com.dida.nowcoder.controller;

import com.dida.nowcoder.entity.User;
import com.dida.nowcoder.service.UserService;
import com.dida.nowcoder.utils.CommunityConstant;
import com.dida.nowcoder.utils.CommunityUtil;
import com.dida.nowcoder.utils.RedisKeyUtil;
import com.google.code.kaptcha.Producer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
public class LoginController implements CommunityConstant {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private UserService userService;

    @Resource
    private Producer kaptchaProducer;

    @Resource
    private RedisTemplate redisTemplate;

    @Value("${server.servlet.context-path}")
    private String contextPath;

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

    //访问忘记密码页面
    @GetMapping("/forget")
    public String forgetPassword() {
        return "/site/forget";
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

    /**
     * 获取生成验证码图片
     *
     * @return 这里返回很特殊，我们返回的是一个图片，所以我们需要手动的使用Response来进行输出
     */
    @GetMapping("/kaptcha")
    public void getKaptcha(HttpServletResponse response/*, HttpSession session*/) {
        //生成验证码
        String text = kaptchaProducer.createText();
        //生成图片
        BufferedImage image = kaptchaProducer.createImage(text);

        //将验证码存入session
        //session.setAttribute("kaptcha", text);

        //将验证码的归属e存入cookie同时设置生存时间以及路径
        String kaptchaOwner = CommunityUtil.generateUUID();
        Cookie cookie = new Cookie("kaptchaOwner", kaptchaOwner);
        cookie.setMaxAge(60);
        cookie.setPath(contextPath);
        response.addCookie(cookie);

        //将验证码存入Redis
        String redisKey = RedisKeyUtil.getKaptchaKey(kaptchaOwner);
        redisTemplate.opsForValue().set(redisKey, text, 60, TimeUnit.SECONDS);



        //将图片输出给浏览器
        response.setContentType("image/png");
        try {
            OutputStream os = response.getOutputStream();
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            logger.error("响应验证码失败："+ e.getMessage());
        }
    }

    /**
     * 登录操作
     *
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param remember 是否记住用户
     * @param model model
     * @param response response
     * @return return
     */
    @PostMapping("/login")
    public String login(String username, String password, String code, boolean remember,
                        Model model/*, HttpSession session*/, HttpServletResponse response,
                        @CookieValue("kaptchaOwner") String kaptchaOwner
    ) {
        //判断验证码
        //String kaptcha = (String) session.getAttribute("kaptcha");
        String kaptcha = null;
        if (StringUtils.isNotBlank(kaptchaOwner)) {
            String redisKey = RedisKeyUtil.getKaptchaKey(kaptchaOwner);
            kaptcha = (String) redisTemplate.opsForValue().get(redisKey);
        }

        if (StringUtils.isBlank(kaptcha) || StringUtils.isBlank(code) || !kaptcha.equalsIgnoreCase(code)) {
            model.addAttribute("codeMsg", "验证码有误，请重新输入");
            return "/site/login";
        }

        //检查账号密码
        int expiredSeconds = remember ? REMEMBER_EXPIRED_SECONDS : DEFAULT_EXPIRED_SECONDS;

        Map<String, Object> map = userService.login(username, password, expiredSeconds);

        if (map.containsKey("ticket")) {
            Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
            cookie.setPath(contextPath);
            cookie.setMaxAge(expiredSeconds);

            response.addCookie(cookie);
            return "redirect:/index";
        } else {
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            return "/site/login";
        }
    }

    /**
     * 登出
     * @param ticket 凭证
     * @return 重定向页面
     */
    @GetMapping("/logout")
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        //我们这里重定向为login，但是我们存在两个login请求，其中一个是get请求，一个是post请求，这里默认会是get请求
        return "redirect:/login";
    }

/*
    @GetMapping("")
    public String getCaptcha() {
        return null;
    }
    *//**
     * 修改密码
     * @param email
     * @param password
     * @return
     *//*
    public String updatePassword(String email, String password) {
        userService.updatePassword(email, password);
        return null;
    }

    @GetMapping("/demo")
    public String demo() {
        return "/mail/demo";
    }*/
}
