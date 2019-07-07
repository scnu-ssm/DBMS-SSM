package com.chenrong.controller;

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

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	
	// 用户的注册, 注册的结果 通过json传回给前端
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult Register(@Param("user") User user) {
		
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
		
		if(taf) // 注册成功   返回账号Id
		{
		System.out.println("I am here!");	
		int userId = userService.getUserId(user).getId();
		ScnuResult msg = new ScnuResult();
		msg.setCode(Const.SUCCESS);
		msg.setStatus("您的账号为:  " + userId);
		return msg;
		}
		
		// 原则上不可能到这里，注册失败
		return null;
		
	}
	
	// 登录使用账号 id 和 password 登录
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult Login(@Param("user") User user) {
		
		boolean taf = false;
		taf = userService.Login(user);
		// 登录成功
		if(taf)
		return ScnuResult.loginSuccess();
		
		// 登录失败
		return ScnuResult.loginFailure();
		
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult Update(@Param("user") User user) {
		
		boolean taf = false;
		taf = userService.Update(user);
		// 更新成功
		if(taf)
		return ScnuResult.updateSuccess();
		
		// 更新失败
		return ScnuResult.updateFailure();
	}

}
