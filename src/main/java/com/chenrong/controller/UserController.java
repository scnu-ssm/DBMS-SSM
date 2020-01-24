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
		if(num == 0)
	    return ScnuResult.registerFailureNull();
		else if(num == 1)
		return ScnuResult.registerFailureUsername();
		else if(num == 2)
		return ScnuResult.registerFailureEmail();
		else if(num == 3)
		return ScnuResult.registerFailureTelephone();
		else if(num == 4)
		return ScnuResult.registerFailureSpace();
		
		boolean taf = false;
		// 通过检测 ，准备注册
		taf = userService.Register(user);
		
		if(taf) // 注册成功   返回账号 username
		{
		String username = user.getUsername();
		ScnuResult msg = new ScnuResult();
		msg.setCode(Const.SUCCESS);
		msg.setStatus("欢迎您:  " + username + " !");
		return msg;
		}
		
		// 原则上不可能到这里，注册失败
		return null;
		
	}
	
	// 登录使用账号 username 和 password 登录，设置Session、Cookie
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult Login(@Param("user") User user, HttpServletRequest request, HttpServletResponse response) {
		
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
		   return ScnuResult.loginSuccess();
		}
		
		// 登录失败
		return ScnuResult.loginFailure();
		
	}
	
	// 修改密码，需要输入原来的密码验证
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult Update(@Param("user") User user, String restPassword) {
		
		boolean taf = false;
		taf = userService.Update(user, restPassword);
		// 更新成功
		if(taf)
		return ScnuResult.updateSuccess();
		
		// 更新失败
		return ScnuResult.updateFailure();
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
			   boolean taf = userService.Update(user, restPassword);
			   if(taf) {
				    // 删除原来的验证码信息
				    session.removeAttribute(Const.VALIDATA_CODE);
				    session.removeAttribute(Const.VALIDATE_USERNAME);
				    session.setMaxInactiveInterval(0);
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
	
	// 退出登录
	@RequestMapping("/logout")
	@ResponseBody
	public ScnuResult logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return ScnuResult.build("退出成功");
	}
	
	// 查询本地用户，通过username查询
	@RequestMapping(value = "/selectUserByUsername", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult getUserByUsername(String username) {
		   if(username == null) {
			  ScnuResult.build(null);
		   }
		   return ScnuResult.build(userService.getUserByUserName(username));
	}
	
	// 查询本地用户，通过userId查询
	@RequestMapping(value = "/selectUserByUserId", method = RequestMethod.POST)
	@ResponseBody
    public ScnuResult getUserByUserId(String userId) {
    	   if(userId == null) {
    		  ScnuResult.build(null);
		   }
    	   return ScnuResult.build(userService.getUserByUserId(userId));
	}

}
