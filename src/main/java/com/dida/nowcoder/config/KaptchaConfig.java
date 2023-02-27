package com.dida.nowcoder.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KaptchaConfig {

    /**
     * 生成验证码
     * @return 返回
     */
    @Bean
    public Producer kaptchaProducer() {
        Properties properties = new Properties();
        properties.setProperty("kaptcha.img.width", "100"); //图片宽度
        properties.setProperty("kaptcha.img.height", "40"); //图片高度
        properties.setProperty("kaptcha.textproducer.font.size", "32"); //验证码字体大小
        properties.setProperty("kaptcha.textproducer.font.color", "0,0,0"); //验证码字体颜色
        properties.setProperty("kaptcha.textproducer.char.string", "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"); //验证码范围
        properties.setProperty("kaptcha.textproducer.char.length", "4"); //验证码长度
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise"); //对验证码进行一定程度的干扰

        DefaultKaptcha kaptcha = new DefaultKaptcha();
        Config config = new Config(properties);
        kaptcha.setConfig(config);

        return kaptcha;
    }
}
