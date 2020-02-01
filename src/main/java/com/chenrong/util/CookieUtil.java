package com.chenrong.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chenrong.bean.Const;

// ����Cookie, ��ȡCookie, ����Cookie�Ĺ���ʱ��
public class CookieUtil {
	
	// Ĭ��30������Чʱ��
	public final static Integer expireTime = 1800;
	
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
	
	// ����Cookie��domain��path��expire
	public static void setCookie(HttpServletResponse response, String key, String value, Integer expire) {
		   Cookie cookie = new Cookie(key, value);
		   cookie.setMaxAge(expire);
		   cookie.setDomain(Const.cookieDomain);
		   cookie.setPath(Const.cookiePath);
		   response.addCookie(cookie);
	}
	
	// �õ�session��USERID��ֵ
	public static String getUserID(HttpServletRequest request) {
		   HttpSession session = request.getSession();
		   return (String)session.getAttribute(Const.USERID);
	}
	
}
