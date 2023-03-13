package com.dida.nowcoder.service;

import com.dida.nowcoder.entity.LoginTicket;
import com.dida.nowcoder.entity.User;

import java.util.Map;

public interface UserService {

    //通过id获取user对象
    public User getUserById(int id);

    //通过用户名获取对象
    public User getUserByName(String name);

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

    //修改密码
    public int updatePassword(int id, String password);

    //修改头像
    public int updateHeader(int userId, String headerUrl);

    //从缓存当中取值
    User getCache(int userId);

    //初始化缓存数据
    User initCache(int userId);

    //数据变更的时候清除缓存数据
    void clearCache(int userId);
}
