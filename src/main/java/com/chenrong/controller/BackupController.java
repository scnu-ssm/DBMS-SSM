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
	BackupService backupService;
	
	// 备份控制器 导出sql
	@RequestMapping("/backup")
	@ResponseBody
	public ScnuResult BackUp(String conenctId, String database, String dest) {
		
		boolean taf = backupService.BackUp(conenctId, database, dest);
		
		if(taf) {
			return ScnuResult.build("转存SQL文件成功");
		}else {
			return ScnuResult.forbidden("转存SQL文件失败");
		}
		
	}
	
	
	// 还原控制器 导入sql
	@RequestMapping("/recovery")
	@ResponseBody
	public ScnuResult Recovery(String conenctId, String database, String src) {
		
		Integer status = 0;
		try {
			status = backupService.Recovery(conenctId, database, src);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(status.equals(-1)) {
			return ScnuResult.forbidden("不存在连接");
		}else if(status.equals(0)) {
			return ScnuResult.forbidden("出现异常");
		}else {
			return ScnuResult.build("还原成功");
		}
		
	}
 
}
