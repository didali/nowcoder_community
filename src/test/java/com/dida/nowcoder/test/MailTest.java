package com.dida.nowcoder.test;

import com.dida.nowcoder.utils.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@SpringBootTest
public class MailTest {

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testSentTextMail() {
        mailClient.sendMail("xxx@qq.com", "TEST", "SUCCESS");
    }

    @Test
    public void testSentHtmlMail() {
        Context context = new Context();
        context.setVariable("username", "111");

        String process = templateEngine.process("/mail/demo", context);
        System.out.println(process);

        mailClient.sendMail("22", "HTML_TEST", process);

    }
}
