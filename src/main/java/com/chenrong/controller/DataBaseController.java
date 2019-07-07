package com.chenrong.controller;

import java.util.ArrayList;
import java.util.Collections;

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
	public ScnuResult createDateBase(String connectName, String databaseName, String character_set_database, String collation_database) {
		
		System.out.println("创建数据库");
		boolean taf = true;
		try {
			taf = dataBaseService.createDateBase(connectName, databaseName, character_set_database, collation_database);
		} catch (Exception e) {
			taf = false;
			e.printStackTrace();
		}
		
		return ScnuResult.build(taf);
	}
	
	// 删除数据库
	@RequestMapping("/deleteDateBase")
	@ResponseBody
	public ScnuResult deleteDateBase(String connectName, String databaseName) {
		
		System.out.println("删除数据库");
		boolean taf = false;
		try {
			taf = dataBaseService.deleteDateBase(connectName, databaseName);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return ScnuResult.build(taf);
	}
	
	// 查询数据库
	@RequestMapping("/showDateBase")
	@ResponseBody
	public ScnuResult showDateBase(String connectName) {
		
		System.out.println("查询数据库");
		ArrayList<String> databaseList = null;
		try {
			databaseList = dataBaseService.showDateBase(connectName);
			Collections.sort(databaseList);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return ScnuResult.build(databaseList);
	}
	
	// 查看数据库属性
	@RequestMapping("/showProperty")
	@ResponseBody
	public ScnuResult showProperty(String connectName, String databaseName) {
		
		System.out.println("查看数据库属性");
		
		DataBaseProperty  dataBaseProperty = null;
		try {
			dataBaseProperty = dataBaseService.showProperty(connectName, databaseName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ScnuResult.build(dataBaseProperty);
	}
	
	
	// 修改数据库的属性
    @RequestMapping("/updateDataBase")
    @ResponseBody
    public ScnuResult updateDataBase(String connectName, String databaseName, String character_set_database, String collation_database) {
    	
    	System.out.println("修改数据库的属性");
    	boolean taf = false;
    	try {
			taf = dataBaseService.updateDataBase(connectName, databaseName, character_set_database, collation_database);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return ScnuResult.build(taf);
    }
}
