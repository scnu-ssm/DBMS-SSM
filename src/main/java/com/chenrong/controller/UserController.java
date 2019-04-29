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
	
	
	// �û���ע��, ע��Ľ�� ͨ��json���ظ�ǰ��
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public Msg Register(@Param("user") User user) {
		
		System.out.println("�ҽ���������");
		
		int num = userService.checkRegister(user);
		
		// ע��ʧ��
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
		// ͨ����� ��׼��ע��
		taf = userService.Register(user);
		
		if(taf) // ע��ɹ�   �����˺�Id
		{
		System.out.println("I am here!");	
		int userId = userService.getUserId(user).getId();
		Msg msg = new Msg();
		msg.setBool(Const.SUCCESS);
		msg.setStatus(Const.REGISTER_SUCCESS + "  �����˺�Ϊ:  " + userId);
		return msg;
		}
		
		// ԭ���ϲ����ܵ����ע��ʧ��
		return null;
		
	}
	
	// ��¼ʹ���˺� id �� password ��¼
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Msg Login(@Param("user") User user) {
		
		boolean taf = false;
		taf = userService.Login(user);
		// ��¼�ɹ�
		if(taf)
		return Msg.loginSuccess();
		
		// ��¼ʧ��
		return Msg.loginFailure();
		
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Msg Update(@Param("user") User user) {
		
		boolean taf = false;
		taf = userService.Update(user);
		// ���³ɹ�
		if(taf)
		return Msg.updateSuccess();
		
		// ����ʧ��
		return Msg.updateFailure();
	}

}
