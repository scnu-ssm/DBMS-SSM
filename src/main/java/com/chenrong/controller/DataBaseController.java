package com.chenrong.controller;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.chenrong.bean.DataBaseProperty;
import com.chenrong.bean.ScnuResult;
import com.chenrong.service.DataBaseService;

@RequestMapping("/database")
@Controller
public class DataBaseController {
	
	@Autowired
	DataBaseService dataBaseService;
	
	
	// 创建数据库
	@RequestMapping("/createDateBase")
	@ResponseBody
	public ScnuResult createDateBase(String connectId, String databaseName, String characterSetDatabase, String collationDatabase) {
		
		System.out.println("创建数据库");
		try {
		   ArrayList<String> databaseList = dataBaseService.showDateBase(connectId);
		   for(String str : databaseList) {
				if(str.toLowerCase().equalsIgnoreCase(databaseName.toLowerCase())) {
					    return ScnuResult.forbidden("数据库已经存在");
				}
		   }
		}catch(Exception e) {
	       e.printStackTrace();
		}

		boolean taf = true;
		try {
			taf = dataBaseService.createDateBase(connectId, databaseName, characterSetDatabase, collationDatabase);
		} catch (Exception e) {
			taf = false;
			e.printStackTrace();
		}
		
		if(taf) {
			return ScnuResult.build("创建数据库: " + databaseName + " 成功");
		}else {
			return ScnuResult.build("创建数据库: " + databaseName + " 失败");
		}
	}
	
	// 删除数据库
	@RequestMapping("/deleteDateBase")
	@ResponseBody
	public ScnuResult deleteDateBase(String connectId, String databaseName) {
		
		System.out.println("删除数据库");
		boolean taf = false;
		try {
			taf = dataBaseService.deleteDateBase(connectId, databaseName);
		}catch(Exception e) {
			e.printStackTrace();
		}
		if(taf) {
		   return ScnuResult.build("删除数据库 " + databaseName + " 成功");
		} else {
		   return ScnuResult.forbidden("删除数据库失败");
		}
	}
	
	// 查询数据库
	@RequestMapping("/showDateBase")
	@ResponseBody
	public ScnuResult showDateBase(String connectId) {
		
		System.out.println("查询数据库");
		ArrayList<String> databaseList = null;
		try {
			databaseList = dataBaseService.showDateBase(connectId);
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(databaseList != null) {
		    return ScnuResult.build(databaseList);
		} else {
			return ScnuResult.forbidden("没有数据库");
		}
		
	}
	
	// 查看数据库属性
	@RequestMapping("/showProperty")
	@ResponseBody
	public ScnuResult showProperty(String connectId, String databaseName) {
		
		System.out.println("查看数据库属性");
		
		DataBaseProperty  dataBaseProperty = null;
		try {
			dataBaseProperty = dataBaseService.showProperty(connectId, databaseName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ScnuResult.build(dataBaseProperty);
		
	}
	
	
	// 修改数据库的属性
    @RequestMapping("/updateDataBase")
    @ResponseBody
    public ScnuResult updateDataBase(String connectId, String databaseName, String characterSetDatabase, String collationDatabase) {
    	
    	System.out.println("修改数据库的属性");
    	boolean taf = false;
    	try {
			taf = dataBaseService.updateDataBase(connectId, databaseName, characterSetDatabase, collationDatabase);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	if(taf) {
    	    return ScnuResult.build("修改数据库属性成功");
    	}else {
    		return ScnuResult.forbidden("修改数据库属性失败");
    	}
    	
    }
}
