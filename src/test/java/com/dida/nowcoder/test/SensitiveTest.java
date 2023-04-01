package com.dida.nowcoder.test;

import com.dida.nowcoder.utils.SensitiveFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SensitiveTest {

    @Autowired
    private SensitiveFilter sensitiveFilter;

    /**
     * 测试敏感词过滤
     */
    @Test
    public void testSensitiveFilter() {
        String text1 = "这里可以赌博、嫖娼、开票、哈哈哈";
        String text2 = "这里可以赌博赌博，赌赌博。";
        String text3 = "这里可以赌赌博博，嫖娼赌博，嫖娼嫖赌博";
        String text4 = "这里可以赌☆赌☆博☆博，嫖☆娼☆赌☆博☆，嫖☆娼☆嫖☆赌☆博";

        String s1 = sensitiveFilter.filter(text1);
        String s2 = sensitiveFilter.filter(text2);
        String s3 = sensitiveFilter.filter(text3);
        String s4 = sensitiveFilter.filter(text4);

        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        System.out.println(s4);
    }
}
