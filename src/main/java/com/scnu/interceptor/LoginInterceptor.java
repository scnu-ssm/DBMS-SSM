package com.scnu.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.chenrong.bean.Const;
import com.chenrong.bean.User;
import com.chenrong.util.CookieUtil;

/**
 * 拦截没有登录的用户
 * @author chenrong
 *
 */
public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String userSessionID = CookieUtil.getCookieValue(request, Const.userCookieKey);
		if(userSessionID == null) {
			return false;
		}
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute(userSessionID);
		if(user == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
