package com.dida.nowcoder.utils;

import com.dida.nowcoder.entity.User;
import org.springframework.stereotype.Component;

/**
 * 起到一个容器的作用
 *      持有用户信息，用于代替session对象.
 */
@Component
public class HostHolder {

    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUser(User user) {
        users.set(user);
    }

    public User getUser() {
        return users.get();
    }

    //清理users
    public void clear() {
        users.remove();
    }
}
