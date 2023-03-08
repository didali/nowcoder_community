package com.dida.nowcoder.test;

import com.dida.nowcoder.dao.DiscussPostMapper;
import com.dida.nowcoder.dao.LoginTicketMapper;
import com.dida.nowcoder.dao.MessageMapper;
import com.dida.nowcoder.entity.DiscussPost;
import com.dida.nowcoder.entity.LoginTicket;
import com.dida.nowcoder.entity.Message;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@SpringBootTest
public class MapperTest {

    private static final Logger logger = LoggerFactory.getLogger(MapperTest.class);


    @Autowired
    DiscussPostMapper discussPostMapper;

    @Autowired
    LoginTicketMapper loginTicketMapper;

    @Resource
    MessageMapper messageMapper;

    @Test
    public void testSelectPost() {
        List<DiscussPost> list = discussPostMapper.selectDiscussPost(0, 0, 10);
        for (DiscussPost post : list) {
            System.out.println(post);
        }

        int counts = discussPostMapper.selectDiscussPostRows(111);
        System.out.println(counts);
    }

    @Test
    public void testLog() {
        System.out.println(logger.getName());

        logger.debug("debug11111");
        logger.info("info11111");
        logger.warn("warn11111");
        logger.error("error11111");
    }

    /**
     * 测试LoginTicketMapper
     */
    @Test
    public void testLoginTicketMapper() {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(111);
        loginTicket.setStatus(0);
        loginTicket.setTicket("aaa");
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 1000 * 60));

        loginTicketMapper.insertLoginTicket(loginTicket);



        loginTicketMapper.updateStatus("aaa", 1);

        loginTicketMapper.selectByTicket("aaa");
    }

    @Test
    public void testTime() {
        System.out.println(new Date(System.currentTimeMillis() + 86400));
    }

    @Test
    public void testMessageMapper() {
        //查询当前用户的会话列表
        List<Message> messages = messageMapper.selectConversations(111, 0, 20);

        //查询当前用户的会话数量
        int i = messageMapper.selectConversationCount(151);

        //查询某个会话所包含的私信列表
        List<Message> messages1 = messageMapper.selectLetters("111_112", 0, 10);

        //查询某个会话所包含的私信数量
        int i1 = messageMapper.selectLetterCount("111_112");

        //查询未读的消息数量
        int i2 = messageMapper.selectLetterUnreadCount(131, "111_131");
    }
}
