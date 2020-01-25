package com.scnu.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.chenrong.bean.Const;
import com.chenrong.bean.ScnuResult;
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
			NoLogin(response);
			return false;
		}
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute(userSessionID);
		if(user == null) {
			NoLogin(response);
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
	
	// 没有登录设置提示信息
	public void NoLogin(HttpServletResponse response) {
		
		   System.out.println("处于未登录状态");
		   response.setCharacterEncoding("UTF-8");
           response.setContentType("application/json;charset=UTF-8");
           try {
              PrintWriter pw = response.getWriter();
              // 没有登录信息
              pw.write(JSON.toJSONString(ScnuResult.forbidden("您还没有登录，请先登录")));
              pw.flush();
              pw.close();
           }catch(Exception e) {
        	  e.printStackTrace();
           }
           
	}

}
