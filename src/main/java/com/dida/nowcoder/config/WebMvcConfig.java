package com.dida.nowcoder.config;

import com.dida.nowcoder.interceptor.LoginRequiredInterceptor;
import com.dida.nowcoder.interceptor.LoginTicketInterceptor;
import com.dida.nowcoder.interceptor.MessageInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private LoginTicketInterceptor loginTicketInterceptor;

    @Resource
    private LoginRequiredInterceptor loginRequiredInterceptor;

    @Resource
    private MessageInterceptor messageInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(loginTicketInterceptor)
                .excludePathPatterns( //设置放行不需要拦截的路径
                        "/**/*.css",
                        "/**/*.js",
                        "/**/*.png",
                        "/**/*.jpg,",
                        "/**/*.jpeg"
                );

        registry.addInterceptor(loginRequiredInterceptor)
                .excludePathPatterns(
                        "/**/*.css",
                        "/**/*.js",
                        "/**/*.png",
                        "/**/*.jpg,",
                        "/**/*.jpeg"
                );

        registry.addInterceptor(messageInterceptor)
                .excludePathPatterns(
                        "/**/*.css",
                        "/**/*.js",
                        "/**/*.png",
                        "/**/*.jpg,",
                        "/**/*.jpeg"
                );
    }
}
