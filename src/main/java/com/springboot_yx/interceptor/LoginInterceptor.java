package com.springboot_yx.interceptor;
//进行登录检查的拦截器( 1.配置好拦截器要拦截哪些请求  2.把这些配置放在容器中)

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    /*目标方法执行之前*/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //登录检查逻辑
        HttpSession session = request.getSession();

        Object loginUser = session.getAttribute("userName");

        if(loginUser != null){
            //放行
            return true;
        }

        //拦截住,未登录,跳转到登录页
        request.setAttribute("msg","请先登录!");
        //转发到登录页
        request.getRequestDispatcher("/denglu").forward(request,response);
        return false;
    }

    /*目标方法执行完成以后*/
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle执行{}",modelAndView);
    }

    /*页面渲染以后*/
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("afterCompletion执行异常{}",ex);
    }
}
