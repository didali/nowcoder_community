package com.dida.nowcoder.service;

import com.dida.nowcoder.entity.DiscussPost;

import java.util.List;

public interface DiscussPostService {

    public List<DiscussPost> getDiscussPosts(int userId, int offset, int limit);

    public int getDiscussPostsRows(int userId);
}
