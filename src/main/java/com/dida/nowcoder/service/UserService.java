package com.dida.nowcoder.service;

import com.dida.nowcoder.entity.LoginTicket;
import com.dida.nowcoder.entity.User;

import java.util.Map;

public interface UserService {

    //通过id获取user对象
    public User getUserById(int id);

    //注册
    public Map<String, Object> register(User user);

    //激活注册账号
    public int activation(int userId, String code);

    //登录
    public Map<String, Object> login(String username, String password, int expiredSeconds);

    //退出
    public void logout(String ticket);

    //查询凭证
    public LoginTicket getLoginTicket(String ticket);
}
