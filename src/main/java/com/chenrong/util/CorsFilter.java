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
 * �����������Ĺ����� + ����Session����
 * @author chenrong
 */
@Component
public class CorsFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
 
    }
 
    /**
     * 1������ session��UserID������
     * 2�������������
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)throws IOException, ServletException {
    	
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
    	HttpSession session = request.getSession();
    	// ���е�session������30���ӵĹ���ʱ��
    	session.setMaxInactiveInterval(Const.expireTime);
    	String sessionId = session.getId();
    	// Ϊ�Ự���ÿɳ־û���Cookie
    	CookieUtil.setCookie(response, Const.JSESSIONID, sessionId, Const.expireTime);
    	
        String userSessionID = CookieUtil.getCookieValue(request, Const.userCookieKey);
        String anonymousID = CookieUtil.getCookieValue(request, Const.anonymousCookieKey);
        
        if(anonymousID == null) {
        	anonymousID = GenerateIDUtil.getUUID32();
        	// ���ù���ʱ��Ϊ10�꣬ԭ����������Cookie
        	CookieUtil.setCookie(response, Const.anonymousCookieKey, anonymousID, 10*365*24*60*60);
        }
        
        System.out.println("sessionId = " + sessionId);
        // UserSessionID ������ UserId
        User user = (User)session.getAttribute(userSessionID);
        
       	System.out.println("anonymousID = " + anonymousID);
        // ����userID����
        if(user == null) {
        	session.setAttribute(Const.USERID, anonymousID);
        }else {
        	session.setAttribute(Const.USERID, user.getId());
        	//����Cookie����Чʱ��
        	CookieUtil.setCookie(response, Const.userCookieKey, userSessionID);
        	System.out.println("userId = " + user.getId());
        }
        
        System.out.println("\n\n");
        
        // �����������
 
        // ��Ӧ��ͷָ�� ָ�����Է�����Դ��URI·��
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
 
        //��Ӧ��ͷָ����Ӧ����������Դ��ʱ�����һ�ֻ���ַ���
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
 
        //���� �������������������
        response.setHeader("Access-Control-Max-Age", "3600");
 
        //����  ��֧�������ͷ���Զ���  ���Է��ʵ�����ͷ  ���磺Token��
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, X-Custom-Header, HaiYi-Access-Token, Token");
 
        //�Զ��� ���Ա����ʵ���Ӧͷ
        response.setHeader("Access-Control-Expose-Headers","checkTokenResult");
 
        // ָʾ���������Ӧ�Ƿ���Ա�¶�ڸ�ҳ�档��trueֵ����ʱ�����Ա���¶
        response.setHeader("Access-Control-Allow-Credentials","true");
        
        filterChain.doFilter(servletRequest, servletResponse);
 
    }
 
    public void destroy() {
 
    }
}
 