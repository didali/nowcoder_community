package com.dida.nowcoder.dao;

import com.dida.nowcoder.entity.LoginTicket;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Deprecated //声明这个组件不推荐使用了
public interface LoginTicketMapper {

    //插入凭证
    int insertLoginTicket(LoginTicket loginTicket);

    //查找凭证
    LoginTicket selectByTicket(String ticket);

    //修改用户状态
    int updateStatus(String ticket, int status);
}
