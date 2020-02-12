package com.chenrong.controller;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.chenrong.bean.ScnuResult;
import com.chenrong.service.BackupService;
import com.chenrong.util.DeleteTask;

/**
 *  该类控制SQL文件的导出和导入
 * @author chenrong
 *
 */
@Controller
@RequestMapping("/mysql")
public class BackupController {
	
	@Autowired
	BackupService backupService;
	
	// 创建删除临时文件的定时线程池
	private ScheduledExecutorService pool = Executors.newScheduledThreadPool(15);
	
	// 备份控制器 导出sql,以文件下载的形式返回前端
	@RequestMapping(value = "/backup", method=RequestMethod.POST)
	@ResponseBody
	public ScnuResult BackUp(String connectId, String database, @RequestParam("tables") List<String> tables, boolean isOnlyStructor, HttpServletResponse response) {
		
		if(tables == null || tables.size() == 0) {
			return ScnuResult.forbidden("不进行任何操作");
		}
		
		try {
			String path = backupService.Backup(connectId, database, tables, isOnlyStructor);
			// 设置下载文件的名字
			response.setHeader("Content-Disposition", "attachment; filename=" + database + ".sql");
			OutputStream outputStream = response.getOutputStream();
			
			FileInputStream fis = new FileInputStream(path);
			int len = 0;
			byte[] bytes = new byte[2048];
			// 读取至没有数据为止
			while((len = fis.read(bytes)) > 0) {
				outputStream.write(bytes, 0, len);
			}
			fis.close();
			outputStream.close();
			
			// 删除临时文件任务线程
			DeleteTask task = new DeleteTask(path);
			// 异步处理删除文件
			pool.schedule(task, 10, TimeUnit.SECONDS);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ScnuResult.forbidden("转存SQL文件失败");
		}
		
		return ScnuResult.build("转存SQL文件成功");
		
	}
	
	
	// 还原控制器 上传SQL文件，并还原到指定数据库
	@RequestMapping(value = "/recovery", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult Recovery(String conenctId, String database, @RequestParam("sqlFile") MultipartFile sqlFile) {
		
		System.out.println("conenctId = " + conenctId);
		System.out.println("database = " + database);
		System.out.println("name = " + sqlFile.getName());
		
		Integer status = 0;
		try {
			status = backupService.Recovery(conenctId, database, sqlFile);
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
    public ScnuResult leadingOutCSV(String connectId, String database, @RequestParam("tables") List<String> tables, HttpServletResponse response) {

    	if(connectId == null || database == null || tables == null || tables.size() == 0) {
    		     return ScnuResult.forbidden("参数缺失，导出内容失败");
    	}
    	
    	List<String> paths = new ArrayList();
    	try {
    	   // 导出单张表，直接返回csv文件，再下载
    	   if(tables.size() == 1) {
    		  String table = tables.get(0);
    		  String path = backupService.leadingOutCSV(connectId, database, table);
    		  
    		  // 下载文件逻辑
    		  response.setHeader("Content-Disposition", "attachment; filename=" + table + ".csv");
    		  FileInputStream fis = new FileInputStream(path);
    		  OutputStream outputStream = response.getOutputStream();
    		  int len = 0;
    		  byte[] bytes = new byte[2048];
    		  while((len = fis.read(bytes)) > 0) {
    			  outputStream.write(bytes, 0, len);
    		  }
    		  outputStream.close();
    		  fis.close();
    		  
    		  paths.add(path);
    	  }else{  // 导出多张表，压缩为zip文件,再下载
    		  
    		  response.setHeader("Content-Disposition", "attachment; filename=" + database + ".zip");
    		  OutputStream outputStream = response.getOutputStream();
    		  ZipOutputStream zos = new ZipOutputStream(outputStream);
    		  for(String table : tables) {
    			  String path = backupService.leadingOutCSV(connectId, database, table);
    			  ZipEntry zipEntry = new ZipEntry(table + ".csv");
    			  zos.putNextEntry(zipEntry);
    			  
    			  // 将csv文件写入zos
    			  FileInputStream fis = new FileInputStream(path);
    			  int len = 0;
    			  byte[] bytes = new byte[2048];
    			  while((len = fis.read(bytes)) > 0) {
    				  zos.write(bytes, 0, len);
    			  }
    			  fis.close();
    			  
    			  paths.add(path);
    		  }
    		  // 关闭zip流
    		  zos.close();
    	  }

          // 清除临时文件
    	   DeleteTask task = new DeleteTask(paths);
    	   pool.schedule(task, 10, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
			return ScnuResult.forbidden("服务器发生异常错误");
		}
    	
    	return ScnuResult.build("导出内容到CSV文件成功");
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
        		
        		// 删除临时文件任务线程
        		DeleteTask task = new DeleteTask(path);
        		// 异步处理删除文件
        		pool.schedule(task, 10, TimeUnit.SECONDS);
        		
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
        System.out.println("tables : ");
        for(String table : tables) {
        	 System.out.println(table);
        }
    	try {
    	// 备份数据
        String path = backupService.Backup(srcConnectId, srcDatabase, tables, isOnlyStructor);
        // 传输数据
        backupService.Recovery(destconnectId, destDatabase, path);
        
		// 删除临时文件任务线程
		DeleteTask task = new DeleteTask(path);
		// 异步处理删除文件
		pool.schedule(task, 10, TimeUnit.SECONDS);
		
    	}catch(Exception e) {
    		e.printStackTrace();
    		return ScnuResult.forbidden("数据传输失败");
    	}
    		 
    	return ScnuResult.build("数据传输成功");
    }
    
    
 
}
