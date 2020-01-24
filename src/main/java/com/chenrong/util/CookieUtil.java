package com.chenrong.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// ����Cookie, ��ȡCookie, ����Cookie�Ĺ���ʱ��
public class CookieUtil {
	
	public final static Integer expireTime = 3600;
	
	public final static String DEMAIN = "localhost";  // Cookie����
	
	public final static String PATH = "/";  // Cookie��·��
	
	// ����key��ȡCookie��value; 
	// key�����ִ�Сд
	public static String getCookieValue(HttpServletRequest request, String Key) {
		   Cookie[] cookies = request.getCookies();
		   String value = null;
		   if(cookies != null) {
		        for(Cookie cookie : cookies) {
			        if(cookie.getName().equalsIgnoreCase(Key.toLowerCase())) {
			    	    value = cookie.getValue();
			    	    break;
			        }
		        }
		   }
		   return value;
	}
	
	// ����Cookie���������ù���ʱ��, Ĭ��30����
	public static void setCookie(HttpServletResponse response, String key, String value) {
		   setCookie(response, key, value, expireTime);
	}
	
	// ����Cookie�������������ù���ʱ��
	public static void setCookie(HttpServletResponse response, String key, String value, Integer expire) {
		   Cookie cookie = new Cookie(key, value);
		   cookie.setMaxAge(expire);
		   cookie.setDomain(DEMAIN);
		   cookie.setPath(PATH);
		   response.addCookie(cookie);
	}
	
}
