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
	
	
	// �������ݿ�
	@RequestMapping("/createDateBase")
	@ResponseBody
	public ScnuResult createDateBase(String connectName, String databaseName, String character_set_database, String collation_database) {
		
		System.out.println("�������ݿ�");
		boolean taf = true;
		try {
			taf = dataBaseService.createDateBase(connectName, databaseName, character_set_database, collation_database);
		} catch (Exception e) {
			taf = false;
			e.printStackTrace();
		}
		
		return ScnuResult.build(taf);
	}
	
	// ɾ�����ݿ�
	@RequestMapping("/deleteDateBase")
	@ResponseBody
	public ScnuResult deleteDateBase(String connectName, String databaseName) {
		
		System.out.println("ɾ�����ݿ�");
		boolean taf = false;
		try {
			taf = dataBaseService.deleteDateBase(connectName, databaseName);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return ScnuResult.build(taf);
	}
	
	// ��ѯ���ݿ�
	@RequestMapping("/showDateBase")
	@ResponseBody
	public ScnuResult showDateBase(String connectName) {
		
		System.out.println("��ѯ���ݿ�");
		ArrayList<String> databaseList = null;
		try {
			databaseList = dataBaseService.showDateBase(connectName);
			Collections.sort(databaseList);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return ScnuResult.build(databaseList);
	}
	
	// �鿴���ݿ�����
	@RequestMapping("/showProperty")
	@ResponseBody
	public ScnuResult showProperty(String connectName, String databaseName) {
		
		System.out.println("�鿴���ݿ�����");
		
		DataBaseProperty  dataBaseProperty = null;
		try {
			dataBaseProperty = dataBaseService.showProperty(connectName, databaseName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ScnuResult.build(dataBaseProperty);
	}
	
	
	// �޸����ݿ������
    @RequestMapping("/updateDataBase")
    @ResponseBody
    public ScnuResult updateDataBase(String connectName, String databaseName, String character_set_database, String collation_database) {
    	
    	System.out.println("�޸����ݿ������");
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
