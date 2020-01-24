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
	
	// ��¼ʹ���˺� username �� password ��¼������Session��Cookie
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult Login(@Param("user") User user, HttpServletRequest request, HttpServletResponse response) {
		
		boolean taf = false;
		taf = userService.Login(user);
		// ��¼�ɹ�
		if(taf) {
		   // ����Session����
		   HttpSession session = request.getSession();
		   User realUser = userService.getUserByUserName(user.getUsername());
		   String userSessionID = GenerateIDUtil.getUUID32();
		   session.setAttribute(userSessionID, realUser);
		   // ����Cookie����Ч����30����
		   CookieUtil.setCookie(response, Const.userCookieKey, userSessionID);
		   return ScnuResult.loginSuccess();
		}
		
		// ��¼ʧ��
		return ScnuResult.loginFailure();
		
	}
	
	// �޸����룬��Ҫ����ԭ����������֤
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult Update(@Param("user") User user, String restPassword) {
		
		boolean taf = false;
		taf = userService.Update(user, restPassword);
		// ���³ɹ�
		if(taf)
		return ScnuResult.updateSuccess();
		
		// ����ʧ��
		return ScnuResult.updateFailure();
	}
	
	// �������룬��Ҫ������֤������֤, �Ӷ��޸�����
	@RequestMapping("/UpdatePasswordByCode")
	@ResponseBody
	public ScnuResult UpdatePasswordByCode(String code, String restPassword, HttpServletRequest request) {
		   HttpSession session = request.getSession();
		   String validateCode = (String)session.getAttribute(Const.VALIDATA_CODE);
		   String validateUsername = (String)session.getAttribute(Const.VALIDATE_USERNAME);
		   if(validateCode == null || validateUsername == null) {
			      return ScnuResult.forbidden("��û�����뷢�����������ʼ�!");
		   }
		   if(validateCode.equals(code)) {
			   User user = userService.getUserByUserName(validateUsername);
			   boolean taf = userService.Update(user, restPassword);
			   if(taf) {
				    // ɾ��ԭ������֤����Ϣ
				    session.removeAttribute(Const.VALIDATA_CODE);
				    session.removeAttribute(Const.VALIDATE_USERNAME);
				    session.setMaxInactiveInterval(0);
			        return ScnuResult.build("�޸�����ɹ�");
			   }
		   }else {
			   return ScnuResult.forbidden("�����6λ��֤�����");
		   }
		   return ScnuResult.forbidden("�޸�����ʧ��");
	}
	
	// ������֤�룬ͨ���ʼ�����
	@RequestMapping("/generateCode")
	@ResponseBody
	public ScnuResult generateCode(String username, HttpServletRequest request) {
		   User user = userService.getUserByUserName(username);
		   if(user == null) {
			    return ScnuResult.forbidden("�û�������");
		   }
		   
		   String email = user.getEmail();
		   String code = GenerateIDUtil.getCode();
		   
		   // ��validateUsername��validateCode��Ϣ�����Session
		   HttpSession session = request.getSession();
           session.setAttribute(Const.VALIDATE_USERNAME, username);
           session.setAttribute(Const.VALIDATA_CODE, code);
           
           System.out.println("��֤��Ϊ: " + code);
		   
		   // ���÷��ʼ��ӿ�
		   String content = new String("Hello, " + username + " ! <br>"
				    + "Enter this 6 digit code to confirm your identity: <br><br>"
				    + code);
		   mainClient.sendMain(email, Const.SUBJECT, content);
		   
		   return ScnuResult.build(email);
	}
	
	// �˳���¼
	@RequestMapping("/logout")
	@ResponseBody
	public ScnuResult logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return ScnuResult.build("�˳��ɹ�");
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
