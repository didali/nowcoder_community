package com.dida.nowcoder.service.impl;

import com.dida.nowcoder.dao.CommentMapper;
import com.dida.nowcoder.entity.Comment;
import com.dida.nowcoder.service.CommentService;
import com.dida.nowcoder.service.DiscussPostService;
import com.dida.nowcoder.utils.CommunityConstant;
import com.dida.nowcoder.utils.SensitiveFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService, CommunityConstant {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private SensitiveFilter sensitiveFilter;

    @Resource
    private DiscussPostService discussPostService;

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

    //添加评论
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int addComment(Comment comment) {
        if (comment == null) {
            throw new IllegalArgumentException("评论不能为空");
        }
        //评论内容不为空的时候，对评论内容进行一些处理
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveFilter.filter(comment.getContent()));

        int rows = commentMapper.insertComment(comment);

        //更新帖子的评论数量
        if (comment.getEntityType() == ENTITY_TYPE_POST) {
            int count = commentMapper.selectCountByEntity(comment.getEntityType(), comment.getEntityId());
            discussPostService.updateCommentCount(comment.getEntityId(), count);
        }
        return rows;
    }
}
