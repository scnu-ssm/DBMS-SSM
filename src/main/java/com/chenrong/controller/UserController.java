package com.chenrong.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chenrong.bean.Const;
import com.chenrong.bean.ScnuResult;
import com.chenrong.bean.User;
import com.chenrong.service.UserService;
import com.chenrong.util.CookieUtil;
import com.chenrong.util.GenerateIDUtil;
import com.scnu.util.MainClient;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	MainClient mainClient;
	
	
	// 用户的注册, 注册的结果 通过json传回给前端
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult Register(@Param("user") User user) {
		
		// 设置主键
		user.setId(GenerateIDUtil.getUUID32());
		int num = userService.checkRegister(user);
		
		// 注册失败
		if(num == 0) {
	       return ScnuResult.forbidden("账号或者密码不能为空");
		}
		else if(num == 1) {
		   return ScnuResult.forbidden("注册失败，用户名冲突!");
		}
		else if(num == 2) {
		   return ScnuResult.forbidden("注册失败，邮箱冲突!");
		}
		else if(num == 4) {
		   return ScnuResult.forbidden("注册失败，账号或者密码不能带有空格!");
		}
		
		boolean taf = false;
		// 通过检测 ，准备注册
		taf = userService.Register(user);
		
		if(taf) // 注册成功   返回账号 username
		{
		   String username = user.getUsername();
		   String data = "欢迎您:  " + username + " !";
		   return ScnuResult.build(data);
		}
		
		// 原则上不可能到这里，注册失败
		return ScnuResult.forbidden("注册失败");
		
	}
	
	// 登录使用账号 username 和 password 登录，设置Session、Cookie
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult Login(@Param("user") User user, HttpServletRequest request, HttpServletResponse response) {
		
		// 检验空值的情况
		if(user == null || user.getUsername() == null || user.getPassword() == null) {
		   return ScnuResult.forbidden("登录失败，账号或者密码为空值!");
		}
		
		boolean taf = false;
		taf = userService.Login(user);
		// 登录成功
		if(taf) {
		   // 设置Session属性
		   HttpSession session = request.getSession();
		   User realUser = userService.getUserByUserName(user.getUsername());
		   String userSessionID = GenerateIDUtil.getUUID32();
		   session.setAttribute(userSessionID, realUser);
		   // 设置Cookie，有效期是30分钟
		   CookieUtil.setCookie(response, Const.userCookieKey, userSessionID);
		   return ScnuResult.build(realUser.getUsername());
		}
		
		// 登录失败
		return ScnuResult.forbidden("登录失败，账号或者密码错误!");
		
	}
	
	// 修改密码，需要输入原来的密码验证，并且需要处于登录状态
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult Update(String password, String restPassword, HttpServletRequest request) {
		
		String userId = CookieUtil.getUserID(request);
		if(password == null || restPassword == null) {
			 return ScnuResult.forbidden("原来的密码或者重置的密码不能为空");
		}
		boolean taf = false;
		taf = userService.Update(userId, password, restPassword);
		// 更新成功
		if(taf) {
		    return ScnuResult.build("更新密码成功");
		}
		// 更新失败
		return ScnuResult.forbidden("原来的密码错误");
		
	}
	
	// 忘记密码，需要邮箱验证码来验证, 从而修改密码
	@RequestMapping("/UpdatePasswordByCode")
	@ResponseBody
	public ScnuResult UpdatePasswordByCode(String code, String restPassword, HttpServletRequest request) {
		   HttpSession session = request.getSession();
		   String validateCode = (String)session.getAttribute(Const.VALIDATA_CODE);
		   String validateUsername = (String)session.getAttribute(Const.VALIDATE_USERNAME);
		   if(validateCode == null || validateUsername == null) {
			      return ScnuResult.forbidden("还没有申请发送忘记密码邮件!");
		   }
		   if(validateCode.equals(code)) {
			   User user = userService.getUserByUserName(validateUsername);
			   boolean taf = userService.Update(user.getId(), user.getPassword(), restPassword);
			   if(taf) {
				    // 删除原来的验证码信息
				    session.removeAttribute(Const.VALIDATA_CODE);
				    session.removeAttribute(Const.VALIDATE_USERNAME);
			        return ScnuResult.build("修改密码成功");
			   }
		   }else {
			   return ScnuResult.forbidden("输入的6位验证码错误");
		   }
		   return ScnuResult.forbidden("修改密码失败");
	}
	
	// 产生验证码，通过邮件发送
	@RequestMapping("/generateCode")
	@ResponseBody
	public ScnuResult generateCode(String username, HttpServletRequest request) {
		   User user = userService.getUserByUserName(username);
		   if(user == null) {
			    return ScnuResult.forbidden("用户不存在");
		   }
		   
		   String email = user.getEmail();
		   String code = GenerateIDUtil.getCode();
		   
		   // 将validateUsername、validateCode信息存放在Session
		   HttpSession session = request.getSession();
           session.setAttribute(Const.VALIDATE_USERNAME, username);
           session.setAttribute(Const.VALIDATA_CODE, code);
           
           System.out.println("验证码为: " + code);
		   
		   // 调用发邮件接口
		   String content = new String("Hello, " + username + " ! <br>"
				    + "Enter this 6 digit code to confirm your identity: <br><br>"
				    + code);
		   mainClient.sendMain(email, Const.SUBJECT, content);
		   
		   return ScnuResult.build(email);
	}
	
	// 注销
	@RequestMapping("/logout")
	@ResponseBody
	public ScnuResult logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return ScnuResult.build("注销成功");
	}
	
	// 查询本地用户，通过username查询， 方便调试
	@RequestMapping(value = "/selectUserByUsername", method = RequestMethod.GET)
	@ResponseBody
	public ScnuResult getUserByUsername(String username) {
		   if(username == null) {
			  ScnuResult.build(null);
		   }
		   User user = userService.getUserByUserName(username);
		   // 没有将用户的id和密码置空
		   // user.setId(null);
		   // user.setPassword(null);
		   return ScnuResult.build(user);
	}
	
	// 查询本地用户，通过userId查询，需要处理登录状态
	@RequestMapping(value = "/selectUserByUserId", method = RequestMethod.GET)
	@ResponseBody
    public ScnuResult getUserByUserId(HttpServletRequest request) {
    	   String userId = CookieUtil.getUserID(request);
    	   User user = userService.getUserByUserId(userId);
		   // 将用户的id和密码置空
		   user.setId(null);
		   user.setPassword(null);
		   return ScnuResult.build(user);
	}

}
