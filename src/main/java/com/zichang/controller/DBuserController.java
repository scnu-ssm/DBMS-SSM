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
	
	//创建用户
	@RequestMapping(value="/createuser", method=RequestMethod.POST)
	@ResponseBody
	public ScnuResult createUser(String connectId, String username, String host, String pwd, String repwd) {
		ConnectInfo connectInfo = connectInfoService.selectByConnectId(connectId);
		String uname = connectInfo.getUsername();
		if(!"root".equals(uname))
			return ScnuResult.buildFalure("非root用户不能创建用户");
		if(username == null || "".equals(username))
			return ScnuResult.buildFalure("用户名不能为空");
		if(host == null || "".equals(host))
			return ScnuResult.buildFalure("主机不能为空");
		if(pwd == null || "".equals(pwd))
			return ScnuResult.buildFalure("密码不能为空");
		if(repwd == null || "".equals(repwd))
			return ScnuResult.buildFalure("确认密码不能为空");
		if(!pwd.equals(repwd))
			return ScnuResult.buildFalure("密码不一致");
		try {
			if(dBuserService.createUser(connectId, username, host, repwd) >= 0)
				return ScnuResult.build("创建用户成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ScnuResult.buildFalure("创建用户失败");
	}
	
	//删除用户
	@RequestMapping(value="/dropuser", method=RequestMethod.POST)
	@ResponseBody
	public ScnuResult dropUser(String connectId, String username, String host) {
		ConnectInfo connectInfo = connectInfoService.selectByConnectId(connectId);
		String uname = connectInfo.getUsername();
		if(!"root".equals(uname))
			return ScnuResult.buildFalure("非root用户不能删除用户");
		try {
			if(dBuserService.dropUser(connectId, username, host) >= 0)
				return ScnuResult.build("删除用户成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ScnuResult.buildFalure("删除用户失败");
	}
	
	//查询所有用户
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
	
	//根据username@host查询用户
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
	
	//修改密码
	@RequestMapping(value="/updatepwd", method=RequestMethod.POST)
	@ResponseBody
	public ScnuResult updatePwd(String connectId, String username, String host, String pwd) {
		ConnectInfo connectInfo = connectInfoService.selectByConnectId(connectId);
		String uname = connectInfo.getUsername();
		if(!uname.equals(username))
			return ScnuResult.buildFalure("不能修改非当前连接用户密码");
		try {
			if(dBuserService.updatePwd(connectId, username, host, pwd) >= 0)
				return ScnuResult.build("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ScnuResult.buildFalure("修改失败");
	}
	
	//查询权限
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
			//获取权限 [SELECT, INSERT, UPDATE, CREATE]
			privilege.setPrivs(grantArr[1].trim().split(","));
			//解析权限范围
			String[] grantRange = grantArr[2].trim().split("\\.");
			privilege.setDatabase(grantRange[0]);
			privilege.setTable(grantRange[1]);
			//解析用户信息
			String[] userMsg = grantArr[3].trim().split("@");
			privilege.setUsername(userMsg[0]);
			privilege.setHost(userMsg[1]);
			privileges.add(privilege);
		}
		return ScnuResult.build(privileges);
	}
	
	//添加权限
	@RequestMapping(value="/grant", method=RequestMethod.POST)
	@ResponseBody
	public ScnuResult grant(@RequestBody String jsonStr) {
		JSONObject json = JSONObject.parseObject(jsonStr);
		Privilege privilege = json.getObject("privilege", Privilege.class);
		ConnectInfo connectInfo = connectInfoService.selectByConnectId(privilege.getConnectId());
		String uname = connectInfo.getUsername();
		if(!"root".equals(uname)) {
			return ScnuResult.buildFalure("非root用户无权授予权限");
		}
		try {
			if(dBuserService.grant(privilege) >= 0)
				return ScnuResult.build("授权成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ScnuResult.buildFalure("授权失败");
	}
	
	//回收权限
	@RequestMapping(value="/revoke", method=RequestMethod.POST)
	@ResponseBody
	public ScnuResult revoke(@RequestBody String jsonStr) {
		JSONObject json = JSONObject.parseObject(jsonStr);
		Privilege privilege = json.getObject("privilege", Privilege.class);
		ConnectInfo connectInfo = connectInfoService.selectByConnectId(privilege.getConnectId());
		String uname = connectInfo.getUsername();
		if(!"root".equals(uname)) {
			return ScnuResult.buildFalure("非root用户无权回收权限");
		}
		try {
			if(dBuserService.revoke(privilege) >= 0)
				return ScnuResult.build("回收授权成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ScnuResult.buildFalure("回收授权失败");
	}
}
