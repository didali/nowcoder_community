package com.dida.nowcoder.dao;

import com.dida.nowcoder.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    //根据实体来分页查询评论
    List<Comment> selectCommentsByEntity(int entityType, int entityId, int offset, int limit);

    //查询数据的条目树
    int selectCountByEntity(int entityType, int entityId);
}
