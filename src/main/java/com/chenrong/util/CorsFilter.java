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
        System.out.println("Filter ������ ִ�� ��");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
 
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
 