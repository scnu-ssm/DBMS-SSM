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
		if(num == 0) {
	       return ScnuResult.forbidden("�˺Ż������벻��Ϊ��");
		}
		else if(num == 1) {
		   return ScnuResult.forbidden("ע��ʧ�ܣ��û�����ͻ!");
		}
		else if(num == 2) {
		   return ScnuResult.forbidden("ע��ʧ�ܣ������ͻ!");
		}
		else if(num == 4) {
		   return ScnuResult.forbidden("ע��ʧ�ܣ��˺Ż������벻�ܴ��пո�!");
		}
		
		boolean taf = false;
		// ͨ����� ��׼��ע��
		taf = userService.Register(user);
		
		if(taf) // ע��ɹ�   �����˺� username
		{
		   String username = user.getUsername();
		   String data = "��ӭ��:  " + username + " !";
		   return ScnuResult.build(data);
		}
		
		// ԭ���ϲ����ܵ����ע��ʧ��
		return ScnuResult.forbidden("ע��ʧ��");
		
	}
	
	// ��¼ʹ���˺� username �� password ��¼������Session��Cookie
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult Login(@Param("user") User user, HttpServletRequest request, HttpServletResponse response) {
		
		// �����ֵ�����
		if(user == null || user.getUsername() == null || user.getPassword() == null) {
		   return ScnuResult.forbidden("��¼ʧ�ܣ��˺Ż�������Ϊ��ֵ!");
		}
		
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
		   return ScnuResult.build(realUser.getUsername());
		}
		
		// ��¼ʧ��
		return ScnuResult.forbidden("��¼ʧ�ܣ��˺Ż����������!");
		
	}
	
	// �޸����룬��Ҫ����ԭ����������֤��������Ҫ���ڵ�¼״̬
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult Update(String password, String restPassword, HttpServletRequest request) {
		
		String userId = CookieUtil.getUserID(request);
		if(password == null || restPassword == null) {
			 return ScnuResult.forbidden("ԭ��������������õ����벻��Ϊ��");
		}
		boolean taf = false;
		taf = userService.Update(userId, password, restPassword);
		// ���³ɹ�
		if(taf) {
		    return ScnuResult.build("��������ɹ�");
		}
		// ����ʧ��
		return ScnuResult.forbidden("ԭ�����������");
		
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
			   boolean taf = userService.Update(user.getId(), user.getPassword(), restPassword);
			   if(taf) {
				    // ɾ��ԭ������֤����Ϣ
				    session.removeAttribute(Const.VALIDATA_CODE);
				    session.removeAttribute(Const.VALIDATE_USERNAME);
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
	
	// ע��
	@RequestMapping("/logout")
	@ResponseBody
	public ScnuResult logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return ScnuResult.build("ע���ɹ�");
	}
	
	// ��ѯ�����û���ͨ��username��ѯ�� �������
	@RequestMapping(value = "/selectUserByUsername", method = RequestMethod.GET)
	@ResponseBody
	public ScnuResult getUserByUsername(String username) {
		   if(username == null) {
			  ScnuResult.build(null);
		   }
		   User user = userService.getUserByUserName(username);
		   // û�н��û���id�������ÿ�
		   // user.setId(null);
		   // user.setPassword(null);
		   return ScnuResult.build(user);
	}
	
	// ��ѯ�����û���ͨ��userId��ѯ����Ҫ�����¼״̬
	@RequestMapping(value = "/selectUserByUserId", method = RequestMethod.GET)
	@ResponseBody
    public ScnuResult getUserByUserId(HttpServletRequest request) {
    	   String userId = CookieUtil.getUserID(request);
    	   User user = userService.getUserByUserId(userId);
		   // ���û���id�������ÿ�
		   user.setId(null);
		   user.setPassword(null);
		   return ScnuResult.build(user);
	}

}
