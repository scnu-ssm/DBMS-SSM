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
import com.chenrong.util.GenerateIDUtil;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	
	// �û���ע��, ע��Ľ�� ͨ��json���ظ�ǰ��
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult Register(@Param("user") User user) {
		
		// ��������
		user.setId(GenerateIDUtil.getUUID32());
		int num = userService.checkRegister(user);
		
		// ע��ʧ��
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
		// ͨ����� ��׼��ע��
		taf = userService.Register(user);
		
		if(taf) // ע��ɹ�   �����˺� username
		{
		String username = user.getUsername();
		ScnuResult msg = new ScnuResult();
		msg.setCode(Const.SUCCESS);
		msg.setStatus("��ӭ��:  " + username + " !");
		return msg;
		}
		
		// ԭ���ϲ����ܵ����ע��ʧ��
		return null;
		
	}
	
	// ��¼ʹ���˺� username �� password ��¼
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult Login(@Param("user") User user) {
		
		boolean taf = false;
		taf = userService.Login(user);
		// ��¼�ɹ�
		if(taf)
		return ScnuResult.loginSuccess();
		
		// ��¼ʧ��
		return ScnuResult.loginFailure();
		
	}
	
	// �޸����룬��Ҫ������֤�룬��������
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult Update(@Param("user") User user, String restPassword) {
		
		boolean taf = false;
		taf = userService.Update(user);
		// ���³ɹ�
		if(taf)
		return ScnuResult.updateSuccess();
		
		// ����ʧ��
		return ScnuResult.updateFailure();
	}
	
	// ��ѯ�����û���ͨ��username��ѯ
	@RequestMapping(value = "/selectUserByUsername", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult getUserByUsername(String username) {
		   if(username == null) {
			  ScnuResult.build(null);
		   }
		   return ScnuResult.build(userService.getUserByUserName(username));
	}
	
	// ��ѯ�����û���ͨ��userId��ѯ
	@RequestMapping(value = "/selectUserByUserId", method = RequestMethod.POST)
	@ResponseBody
    public ScnuResult getUserByUserId(String userId) {
    	   if(userId == null) {
    		  ScnuResult.build(null);
		   }
    	   return ScnuResult.build(userService.getUserByUserId(userId));
	}

}
