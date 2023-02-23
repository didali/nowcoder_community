package com.dida.nowcoder.service;

import com.dida.nowcoder.dao.UserMapper;
import com.dida.nowcoder.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    public UserMapper userMapper;

    public User getUserById(int id) {
        return userMapper.selectById(id);
    }
}
