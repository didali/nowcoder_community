package com.dida.nowcoder.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailClient {

    private static final Logger logger = LoggerFactory.getLogger(MailClient.class);

    //核心组件
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sendFrom;

    public void sendMail(String sentTo, String subject, String content) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

            helper.setFrom(sendFrom);
            helper.setTo(sentTo);
            helper.setSubject(subject);
            helper.setText(content, true); //没有加true则默认发送内容文本是普通文本，加上了true则表示支持html文本

            mailSender.send(helper.getMimeMessage());
        } catch (MessagingException e) {
            logger.error("发送邮件失败：" + e.getMessage());
        }

    }

}
