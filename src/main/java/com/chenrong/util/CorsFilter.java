package com.chenrong.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
 
@Component
public class CorsFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
 
    }
 

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)throws IOException, ServletException {
        System.out.println("Filter 过滤器 执行 了");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
 
        // 响应标头指定 指定可以访问资源的URI路径
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
 
        //响应标头指定响应访问所述资源到时允许的一种或多种方法
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
 
        //设置 缓存可以生存的最大秒数
        response.setHeader("Access-Control-Max-Age", "3600");
 
        //设置  受支持请求标头（自定义  可以访问的请求头  例如：Token）
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, X-Custom-Header, HaiYi-Access-Token, Token");
 
        //自定义 可以被访问的响应头
        response.setHeader("Access-Control-Expose-Headers","checkTokenResult");
 
        // 指示的请求的响应是否可以暴露于该页面。当true值返回时它可以被暴露
        response.setHeader("Access-Control-Allow-Credentials","true");
        
        filterChain.doFilter(servletRequest, servletResponse);
 
    }
 
    public void destroy() {
 
    }
}
 