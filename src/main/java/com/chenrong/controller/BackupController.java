package com.chenrong.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chenrong.bean.ConnectVO;
import com.chenrong.bean.ScnuResult;
import com.chenrong.service.BackupService;

@Controller
@RequestMapping("/mysql")
public class BackupController {
	
	@Autowired
	BackupService backupService;
	
	// 备份控制器 导出sql
	@RequestMapping(value = "/backup", method=RequestMethod.POST)
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
	@RequestMapping(value = "/recovery", method = RequestMethod.POST)
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
	
    // 导出内容到csv文件中
    @RequestMapping(value = "/leadingOutCSV", method = RequestMethod.POST)
    @ResponseBody
    public ScnuResult leadingOutCSV(ConnectVO connectVO, String dest) {

    	if(connectVO == null || connectVO.getConnectId() == null || 
    	   connectVO.getDatabase() == null || connectVO.getTable() == null || dest == null) {
    		    return ScnuResult.forbidden("导出内容失败");
    	}
    	try {
          backupService.leadingOutCSV(connectVO, dest);
		} catch (Exception e) {
			e.printStackTrace();
			return ScnuResult.forbidden("服务器发生异常错误");
		}
    	return ScnuResult.build("导出内容成功");
    	
    }
 
}
