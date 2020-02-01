package com.chenrong.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chenrong.bean.ConnectVO;
import com.chenrong.bean.ScnuResult;
import com.chenrong.service.BackupService;
import com.chenrong.util.GenerateIDUtil;

@Controller
@RequestMapping("/mysql")
public class BackupController {
	
	@Autowired
	BackupService backupService;
	
	// 备份控制器 导出sql
	@RequestMapping(value = "/backup", method=RequestMethod.POST)
	@ResponseBody
	public ScnuResult BackUp(String connectId, String database, @RequestParam("tables") List<String> tables, String dest, boolean isOnlyStructor) {
		
		if(tables == null || tables.size() == 0) {
			return ScnuResult.forbidden("不进行任何操作");
		}
		
		try {
			backupService.Backup(connectId, database, tables, dest, isOnlyStructor);
		} catch (Exception e) {
			e.printStackTrace();
			return ScnuResult.forbidden("转存SQL文件失败");
		}
		
		return ScnuResult.build("转存SQL文件成功");
		
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
			return ScnuResult.forbidden("出现异常");
		}
		if(status.equals(-1)) {
			return ScnuResult.forbidden("不存在连接");
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
    
    // 数据库间的数据传输模块方式2,返回传输成功、失败的情况，较为耗时
    @RequestMapping(value = "dataTransfer2", method = RequestMethod.POST)
    @ResponseBody
    public ScnuResult DataTransfer2(String srcConnectId, String srcDatabase, @RequestParam("tables") List<String> tables, String destconnectId, String destDatabase, boolean isOnlyStructor) {
    	// 若不存在数据表，操作失败
    	if(tables == null || tables.size() == 0) {
    		  return ScnuResult.forbidden("没有进行任何操作");
    	}
    	List<String> success = new ArrayList();
    	List<String> failure = new ArrayList();
    	// 逐个操作，失败放到失败的集合，成功放到成功的集合
    	for(String table : tables) {
    		try {
    			// 备份
        		String path = backupService.BackupTable(srcConnectId, srcDatabase, table, isOnlyStructor);
        		// 传输数据
        		backupService.Recovery(destconnectId, destDatabase, path);
        		// 删除临时的文件
        		File file = new File(path);
        		boolean isDeleteFile = file.delete();
        		if(isDeleteFile) {
        			System.out.println(table + "临时文件删除成功");
        		}else {
        			// 强制删除临时文件
        			for(int i = 0; i < 10 && !file.delete(); i++) {
        				System.gc();
        				System.out.println("第" + (i + 1) + "次进行尝试删除临时文件" + table);
        			}
        		}
    		}catch(Exception e) {
    			failure.add(table);
    			System.out.println("数据传输错误");
    			e.printStackTrace();
    			continue;
    		}
    		    success.add(table);
    	}
    	Map<String, List<String>> map = new HashMap();
    	map.put("success", success);
    	map.put("failure", failure);
    	return ScnuResult.build(map);
    }
    
    // 数据库间的数据传输模块方式，耗时短
    @RequestMapping(value = "dataTransfer", method = RequestMethod.POST)
    @ResponseBody
    public ScnuResult DataTransfer(String srcConnectId, String srcDatabase, @RequestParam("tables") List<String> tables, String destconnectId, String destDatabase, boolean isOnlyStructor) {
    	// 若不存在数据表，操作失败
    	if(tables == null || tables.size() == 0) {
    		  return ScnuResult.forbidden("没有进行任何操作");
    	}

    	String path = BackupService.TempPath + GenerateIDUtil.getUUID32() + BackupService.suffix;
    	try {
    	// 创建文件
    	BackupService.generateFile(path);
    	// 备份数据
        backupService.Backup(srcConnectId, srcDatabase, tables, path, isOnlyStructor);
        // 传输数据
        backupService.Recovery(destconnectId, destDatabase, path);
        // 删除临时文件
        File file = new File(path);
        boolean isDeleteFile = file.delete();
        if(isDeleteFile) {
        	System.out.println("临时文件删除成功");
        }else {
        	System.out.println("临时文件删除失败");
        	 // 强制删除临时文件
        	for(int i = 0; i < 10 && !file.delete(); i++) {
        		    System.gc();
        			System.out.println("第" + (i + 1) + "次重新尝试删除临时文件");
        	}
        }
    	}catch(Exception e) {
    		e.printStackTrace();
    		return ScnuResult.forbidden("数据传输失败");
    	}
    		 
    	return ScnuResult.build("数据传输成功");
    }
    
    
 
}
