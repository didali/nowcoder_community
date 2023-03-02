package com.dida.nowcoder.service;

import com.dida.nowcoder.entity.DiscussPost;

import java.util.List;

public interface DiscussPostService {

    //分页查询帖子
    public List<DiscussPost> getDiscussPosts(int userId, int offset, int limit);

    //查询帖子的行数
    public int getDiscussPostsRows(int userId);

    //发布帖子
    public int addDiscussPost(DiscussPost discussPost);
}
