package com.dida.nowcoder.service.impl;

import com.dida.nowcoder.dao.DiscussPostMapper;
import com.dida.nowcoder.entity.DiscussPost;
import com.dida.nowcoder.service.DiscussPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussPostServiceImpl implements DiscussPostService {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    public List<DiscussPost> getDiscussPosts(int userId, int offset, int limit) {
        return discussPostMapper.selectDiscussPost(userId, offset, limit);
    }

    public int getDiscussPostsRows(int userId) {
        return discussPostMapper.selectDiscussPostRows(userId);
    }
}
