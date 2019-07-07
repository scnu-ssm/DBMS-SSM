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
	
	// ���ݿ����� ����sql
	@RequestMapping("/backup")
	@ResponseBody
	public ScnuResult BackUp(String connectName, String database, String form, String dest) {
		
		ConnectInfo connect = connectInfoService.select(connectName);
		String username = connect.getUsername();
		String password = connect.getPassword();
		boolean taf = backupService.BackUp(username, password, database, form, dest);
		
		return ScnuResult.build(taf);
	}
	
	
	// ��ԭ������ ����sql
	@RequestMapping("/recovery")
	@ResponseBody
	public ScnuResult Recovery(String connectName, String database, String src) {
		
		boolean taf = false;
		ConnectInfo connect = connectInfoService.select(connectName);
		if(connect == null)
			return ScnuResult.build(taf);
		
		try {
			taf = backupService.Recovery(connectName, database, src);
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return ScnuResult.build(taf);
	}
	
	// �ر����ӳص�����
		@RequestMapping("/closeConnect")
		@ResponseBody
		public ScnuResult closeConnect(String connectName) {
			
			boolean taf = false;
			ConnectInfo connect = connectInfoService.select(connectName);
			if(connect == null)
				return ScnuResult.build(taf);
			
			taf = backupService.closeConnect(connect);
			
			return ScnuResult.build(taf);
		}
 
}