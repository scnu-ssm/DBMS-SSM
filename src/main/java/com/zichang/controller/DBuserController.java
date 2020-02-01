package com.zichang.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chenrong.bean.ConnectInfo;
import com.chenrong.bean.ScnuResult;
import com.chenrong.service.ConnectInfoService;
import com.zichang.bean.DBuser;
import com.zichang.bean.Privilege;
import com.zichang.service.DBuserService;

@Controller
@RequestMapping("/dbuser")
public class DBuserController {
	
	@Autowired
	DBuserService dBuserService;
	
	@Autowired
	ConnectInfoService connectInfoService;
	
	@RequestMapping(value="/showusercol", method=RequestMethod.POST)
	@ResponseBody
	public ScnuResult showUserCol(String connectId) {
		List<String> list = new ArrayList<String>();
		try {
			list = dBuserService.showUserCol(connectId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ScnuResult.build(list);
	}
	
	//�����û�
	@RequestMapping(value="/createuser", method=RequestMethod.POST)
	@ResponseBody
	public ScnuResult createUser(String connectId, String username, String host, String pwd, String repwd) {
		ConnectInfo connectInfo = connectInfoService.selectByConnectId(connectId);
		String uname = connectInfo.getUsername();
		if(!"root".equals(uname))
			return ScnuResult.buildFalure("��root�û����ܴ����û�");
		if(username == null || "".equals(username))
			return ScnuResult.buildFalure("�û�������Ϊ��");
		if(host == null || "".equals(host))
			return ScnuResult.buildFalure("��������Ϊ��");
		if(pwd == null || "".equals(pwd))
			return ScnuResult.buildFalure("���벻��Ϊ��");
		if(repwd == null || "".equals(repwd))
			return ScnuResult.buildFalure("ȷ�����벻��Ϊ��");
		if(!pwd.equals(repwd))
			return ScnuResult.buildFalure("���벻һ��");
		try {
			if(dBuserService.createUser(connectId, username, host, repwd) >= 0)
				return ScnuResult.build("�����û��ɹ�");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ScnuResult.buildFalure("�����û�ʧ��");
	}
	
	//ɾ���û�
	@RequestMapping(value="/dropuser", method=RequestMethod.POST)
	@ResponseBody
	public ScnuResult dropUser(String connectId, String username, String host) {
		ConnectInfo connectInfo = connectInfoService.selectByConnectId(connectId);
		String uname = connectInfo.getUsername();
		if(!"root".equals(uname))
			return ScnuResult.buildFalure("��root�û�����ɾ���û�");
		try {
			if(dBuserService.dropUser(connectId, username, host) >= 0)
				return ScnuResult.build("ɾ���û��ɹ�");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ScnuResult.buildFalure("ɾ���û�ʧ��");
	}
	
	//��ѯ�����û�
	@RequestMapping(value="/allusers", method=RequestMethod.POST)
	@ResponseBody
	public ScnuResult showUsers(String connectId){
		List<DBuser> list = new ArrayList<>();
		try {
			list = dBuserService.showUsers(connectId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ScnuResult.build(list);
	}
	
	//����username@host��ѯ�û�
	@RequestMapping(value="/oneuser", method=RequestMethod.POST)
	@ResponseBody
	public ScnuResult selectUserByName(String connectId, String username, String host) {
		DBuser dBuser = new DBuser();
		try {
			dBuser = dBuserService.selectUserByName(connectId, username, host);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ScnuResult.build(dBuser);
	}
	
	//�޸�����
	@RequestMapping(value="/updatepwd", method=RequestMethod.POST)
	@ResponseBody
	public ScnuResult updatePwd(String connectId, String username, String host, String pwd) {
		ConnectInfo connectInfo = connectInfoService.selectByConnectId(connectId);
		String uname = connectInfo.getUsername();
		if(!uname.equals(username))
			return ScnuResult.buildFalure("�����޸ķǵ�ǰ�����û�����");
		try {
			if(dBuserService.updatePwd(connectId, username, host, pwd) >= 0)
				return ScnuResult.build("�޸ĳɹ�");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ScnuResult.buildFalure("�޸�ʧ��");
	}
	
	//��ѯȨ��
	@RequestMapping(value="/showprivs", method=RequestMethod.POST)
	@ResponseBody
	public ScnuResult showPrivs(String connectId, String username, String host) {
		List<String> list = new ArrayList<>();
		try {
			list = dBuserService.showPrivs(connectId, username, host);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Privilege> privileges = new ArrayList<>();
		for(int i = 1; i < list.size(); i++) {
			Privilege privilege = new Privilege();
			String grantStr = list.get(i);
			//grantStr = GRANT SELECT, INSERT, UPDATE, CREATE ON `test`.* TO 'testUser'@'localhost'
			String[] grantArr = grantStr.split("GRANT|ON|TO");
			//��ȡȨ�� [SELECT, INSERT, UPDATE, CREATE]
			privilege.setPrivs(grantArr[1].trim().split(","));
			//����Ȩ�޷�Χ
			String[] grantRange = grantArr[2].trim().split("\\.");
			privilege.setDatabase(grantRange[0]);
			privilege.setTable(grantRange[1]);
			//�����û���Ϣ
			String[] userMsg = grantArr[3].trim().split("@");
			privilege.setUsername(userMsg[0]);
			privilege.setHost(userMsg[1]);
			privileges.add(privilege);
		}
		return ScnuResult.build(privileges);
	}
	
	//���Ȩ��
	@RequestMapping(value="/grant", method=RequestMethod.POST)
	@ResponseBody
	public ScnuResult grant(@RequestBody String jsonStr) {
		JSONObject json = JSONObject.parseObject(jsonStr);
		Privilege privilege = json.getObject("privilege", Privilege.class);
		ConnectInfo connectInfo = connectInfoService.selectByConnectId(privilege.getConnectId());
		String uname = connectInfo.getUsername();
		if(!"root".equals(uname)) {
			return ScnuResult.buildFalure("��root�û���Ȩ����Ȩ��");
		}
		try {
			if(dBuserService.grant(privilege) >= 0)
				return ScnuResult.build("��Ȩ�ɹ�");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ScnuResult.buildFalure("��Ȩʧ��");
	}
	
	//����Ȩ��
	@RequestMapping(value="/revoke", method=RequestMethod.POST)
	@ResponseBody
	public ScnuResult revoke(@RequestBody String jsonStr) {
		JSONObject json = JSONObject.parseObject(jsonStr);
		Privilege privilege = json.getObject("privilege", Privilege.class);
		ConnectInfo connectInfo = connectInfoService.selectByConnectId(privilege.getConnectId());
		String uname = connectInfo.getUsername();
		if(!"root".equals(uname)) {
			return ScnuResult.buildFalure("��root�û���Ȩ����Ȩ��");
		}
		try {
			if(dBuserService.revoke(privilege) >= 0)
				return ScnuResult.build("������Ȩ�ɹ�");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ScnuResult.buildFalure("������Ȩʧ��");
	}
}
