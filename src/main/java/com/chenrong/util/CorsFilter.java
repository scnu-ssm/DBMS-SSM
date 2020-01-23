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
 * �����������Ĺ����� + ����Session���ù���
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
        
        String UserSessionID = CookieUtil.getCookieValue(request, Const.userCookieKey);
        String AnonymousID = CookieUtil.getCookieValue(request, Const.anonymousCookieKey);
        
        if(AnonymousID == null) {
        	AnonymousID = GenerateIDUtil.getUUID32();
        	// ���ù���ʱ��Ϊ10�꣬ԭ����������Cookie
        	CookieUtil.setCookie(response, Const.anonymousCookieKey, AnonymousID, 10*365*24*60*60);
        }
        
        HttpSession session = request.getSession();
        // UserSessionID ������ UserId
        User user = (User)session.getAttribute(UserSessionID);
        
        // ����userID����
        if(user == null) {
        	session.setAttribute(Const.USERID, AnonymousID);
        }else {
        	session.setAttribute(Const.USERID, user.getId());
        }
        
        
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
 