package com.dida.nowcoder.test;

import com.dida.nowcoder.dao.DiscussPostMapper;
import com.dida.nowcoder.entity.DiscussPost;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class MapperTest {

    private static final Logger logger = LoggerFactory.getLogger(MapperTest.class);


    @Autowired
    DiscussPostMapper discussPostMapper;

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
}
