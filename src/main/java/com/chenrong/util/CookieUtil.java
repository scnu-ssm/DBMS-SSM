package com.chenrong.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 设置Cookie, 获取Cookie, 设置Cookie的过期时间
public class CookieUtil {
	
	public final static Integer expireTime = 3600;
	
	public final static String DEMAIN = "localhost";  // Cookie的域
	
	public final static String PATH = "/";  // Cookie的路径
	
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
	
	// 设置Cookie，可以自主设置过期时间
	public static void setCookie(HttpServletResponse response, String key, String value, Integer expire) {
		   Cookie cookie = new Cookie(key, value);
		   cookie.setMaxAge(expire);
		   cookie.setDomain(DEMAIN);
		   cookie.setPath(PATH);
		   response.addCookie(cookie);
	}
	
}
