package com.chenrong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chenrong.bean.ConnectInfo;
import com.chenrong.bean.ScnuResult;
import com.chenrong.service.BackupService;
import com.chenrong.service.ConnectInfoService;

@Controller
@RequestMapping("/mysql")
public class BackupController {
	
	@Autowired
	ConnectInfoService connectInfoService;
	
	@Autowired
	BackupService backupService;
	
	// 备份控制器 导出sql
	@RequestMapping("/backup")
	@ResponseBody
	public ScnuResult BackUp(String conenctId, String database, String form, String dest) {
		
		ConnectInfo connect = connectInfoService.selectByConnectId(conenctId);
		String username = connect.getUsername();
		String password = connect.getPassword();
		boolean taf = backupService.BackUp(username, password, database, form, dest);
		
		return ScnuResult.build(taf);
	}
	
	
	// 还原控制器 导入sql
	@RequestMapping("/recovery")
	@ResponseBody
	public ScnuResult Recovery(String conenctId, String database, String src) {
		
		boolean taf = false;
		ConnectInfo connect = connectInfoService.selectByConnectId(conenctId);
		if(connect == null)
			return ScnuResult.build(taf);
		
		try {
			taf = backupService.Recovery(conenctId, database, src);
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return ScnuResult.build(taf);
	}
	
	// 关闭连接池的连接
		@RequestMapping("/closeConnect")
		@ResponseBody
		public ScnuResult closeConnect(String conenctId) {
			
			boolean taf = false;
			ConnectInfo connect = connectInfoService.selectByConnectId(conenctId);
			if(connect == null)
				return ScnuResult.build(taf);
			
			taf = backupService.closeConnect(connect);
			
			return ScnuResult.build(taf);
		}
 
}
