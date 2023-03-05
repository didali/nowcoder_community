package com.dida.nowcoder.service;

import com.dida.nowcoder.entity.Comment;

import java.util.List;

public interface CommentService {

    //根据实体来分页查询评论
    public List<Comment> getCommentsByEntity(int entityType, int entityId, int offset, int limit);

    //查询数据的条目树
    public int getCountByEntity(int entityType, int entityId);
}
