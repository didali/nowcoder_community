package com.dida.nowcoder.service.impl;

import com.dida.nowcoder.dao.CommentMapper;
import com.dida.nowcoder.entity.Comment;
import com.dida.nowcoder.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    //根据实体来分页查询评论
    @Override
    public List<Comment> getCommentsByEntity(int entityType, int entityId, int offset, int limit) {
        return commentMapper.selectCommentsByEntity(entityType, entityId, offset, limit);
    }

    //查询数据的条目树
    @Override
    public int getCountByEntity(int entityType, int entityId) {
        return commentMapper.selectCountByEntity(entityType, entityId);
    }
}
