package com.chenrong.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chenrong.bean.Const;
import com.chenrong.bean.Msg;
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
	public Msg Register(@Param("user") User user) {
		
		System.out.println("我进来这里了");
		
		int num = userService.checkRegister(user);
		
		// 注册失败
		if(num == 0)
	    return Msg.registerFailureNull();
		else if(num == 1)
		return Msg.registerFailureUsername();
		else if(num == 2)
		return Msg.registerFailureEmail();
		else if(num == 3)
		return Msg.registerFailureTelephone();
		else if(num == 4)
		return Msg.registerFailureSpace();
		
		boolean taf = false;
		// 通过检测 ，准备注册
		taf = userService.Register(user);
		
		if(taf) // 注册成功   返回账号Id
		{
		System.out.println("I am here!");	
		int userId = userService.getUserId(user).getId();
		Msg msg = new Msg();
		msg.setBool(Const.SUCCESS);
		msg.setStatus(Const.REGISTER_SUCCESS + "  您的账号为:  " + userId);
		return msg;
		}
		
		// 原则上不可能到这里，注册失败
		return null;
		
	}
	
	// 登录使用账号 id 和 password 登录
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Msg Login(@Param("user") User user) {
		
		boolean taf = false;
		taf = userService.Login(user);
		// 登录成功
		if(taf)
		return Msg.loginSuccess();
		
		// 登录失败
		return Msg.loginFailure();
		
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Msg Update(@Param("user") User user) {
		
		boolean taf = false;
		taf = userService.Update(user);
		// 更新成功
		if(taf)
		return Msg.updateSuccess();
		
		// 更新失败
		return Msg.updateFailure();
	}

}
