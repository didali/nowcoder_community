package com.dida.nowcoder.interceptor;

import com.dida.nowcoder.annotation.LoginRequired;
import com.dida.nowcoder.utils.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断拦截的目标是否是一个方法，
        //HandlerMethod是SpringMvc提供的一个类型，如果我们拦截的目标是一个方法的话，那么这个对象将是这个类型，这是一个隐含的规则
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            //获取方法上的注解
            LoginRequired required = method.getAnnotation(LoginRequired.class);
            //判断required是否为空(即是否是需要拦截的路径)，如果不为空判断用户是否登录
            if (required != null && hostHolder.getUser() == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return false;
            }
        }
        return true;
    }
}
