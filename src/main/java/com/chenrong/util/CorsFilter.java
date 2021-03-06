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
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.chenrong.bean.Const;
import com.chenrong.bean.User;
 
/**
 * 解决跨域请求的过滤器 + 设置Session属性
 * @author chenrong
 */
@Component
public class CorsFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
 
    }
 
    /**
     * 1、设置 session的UserID的属性
     * 2、解决跨域请求
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)throws IOException, ServletException {
    	
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
    	HttpSession session = request.getSession();
    	// 所有的session都设置30分钟的过期时间
    	session.setMaxInactiveInterval(Const.expireTime);
    	String sessionId = session.getId();
    	// 为会话设置可持久化的Cookie
    	CookieUtil.setCookie(response, Const.JSESSIONID, sessionId, Const.expireTime);
    	
        String userSessionID = CookieUtil.getCookieValue(request, Const.userCookieKey);
        String anonymousID = CookieUtil.getCookieValue(request, Const.anonymousCookieKey);
        
        if(anonymousID == null) {
        	anonymousID = GenerateIDUtil.getUUID32();
        	// 设置过期时间为10年，原则上算永久Cookie
        	CookieUtil.setCookie(response, Const.anonymousCookieKey, anonymousID, 10*365*24*60*60);
        }
        
        System.out.println("sessionId = " + sessionId);
        // UserSessionID 不等于 UserId
        User user = (User)session.getAttribute(userSessionID);
        
       	System.out.println("anonymousID = " + anonymousID);
        // 设置userID属性
        if(user == null) {
        	session.setAttribute(Const.USERID, anonymousID);
        }else {
        	session.setAttribute(Const.USERID, user.getId());
        	//更新Cookie的有效时间
        	CookieUtil.setCookie(response, Const.userCookieKey, userSessionID);
        	System.out.println("userId = " + user.getId());
        }
        
        System.out.println("\n\n");
        
        // 解决跨域请求
 
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
 