package com.chenrong.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chenrong.bean.Const;

// 设置Cookie, 获取Cookie, 设置Cookie的过期时间
public class CookieUtil {
	
	// 默认30分钟有效时间
	public final static Integer expireTime = 1800;
	
	// 根据key获取Cookie的value; 
	// key不区分大小写
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
	
	// 设置Cookie，并且设置过期时间, 默认30分钟
	public static void setCookie(HttpServletResponse response, String key, String value) {
		   setCookie(response, key, value, expireTime);
	}
	
	// 设置Cookie的domain、path、expire
	public static void setCookie(HttpServletResponse response, String key, String value, Integer expire) {
		   Cookie cookie = new Cookie(key, value);
		   cookie.setMaxAge(expire);
		   cookie.setDomain(Const.cookieDomain);
		   cookie.setPath(Const.cookiePath);
		   response.addCookie(cookie);
	}
	
	// 得到session的USERID的值
	public static String getUserID(HttpServletRequest request) {
		   HttpSession session = request.getSession();
		   return (String)session.getAttribute(Const.USERID);
	}
	
}
