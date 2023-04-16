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

    //查询帖子详情
    public DiscussPost getDiscussPostById(int id);

    //修改帖子评论数量
    public int updateCommentCount(int id, int commentCount);

    //删除帖子
    public int deleteDiscussPost(int id);
}
