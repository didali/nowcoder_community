package com.dida.nowcoder.dao;

import com.dida.nowcoder.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

    //offset:起始行的行号 limit:每一页最多显示多少行数据
    List<DiscussPost> selectDiscussPost(int userId, int offset, int limit);

    //查询帖子的行数
    //@Param这个注解是用于给参数取别名的，当只有一个参数，并且在if标签当中使用的话，则必须加上别名
    int selectDiscussPostRows(@Param("userId") int userId);

    int insertDiscussPost(DiscussPost discussPost);
}
