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
	
	
	// �������ݿ�
	@RequestMapping("/createDateBase")
	@ResponseBody
	public ScnuResult createDateBase(String connectId, String databaseName, String characterSetDatabase, String collationDatabase) {
		
		System.out.println("�������ݿ�");
		try {
		   ArrayList<String> databaseList = dataBaseService.showDateBase(connectId);
		   for(String str : databaseList) {
				if(str.toLowerCase().equalsIgnoreCase(databaseName.toLowerCase())) {
					    return ScnuResult.forbidden("���ݿ��Ѿ�����");
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
			return ScnuResult.build("�������ݿ�: " + databaseName + " �ɹ�");
		}else {
			return ScnuResult.build("�������ݿ�: " + databaseName + " ʧ��");
		}
	}
	
	// ɾ�����ݿ�
	@RequestMapping("/deleteDateBase")
	@ResponseBody
	public ScnuResult deleteDateBase(String connectId, String databaseName) {
		
		System.out.println("ɾ�����ݿ�");
		boolean taf = false;
		try {
			taf = dataBaseService.deleteDateBase(connectId, databaseName);
		}catch(Exception e) {
			e.printStackTrace();
		}
		if(taf) {
		   return ScnuResult.build("ɾ�����ݿ� " + databaseName + " �ɹ�");
		} else {
		   return ScnuResult.forbidden("ɾ�����ݿ�ʧ��");
		}
	}
	
	// ��ѯ���ݿ�
	@RequestMapping("/showDateBase")
	@ResponseBody
	public ScnuResult showDateBase(String connectId) {
		
		System.out.println("��ѯ���ݿ�");
		ArrayList<String> databaseList = null;
		try {
			databaseList = dataBaseService.showDateBase(connectId);
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(databaseList != null) {
		    return ScnuResult.build(databaseList);
		} else {
			return ScnuResult.forbidden("û�����ݿ�");
		}
		
	}
	
	// �鿴���ݿ�����
	@RequestMapping("/showProperty")
	@ResponseBody
	public ScnuResult showProperty(String connectId, String databaseName) {
		
		System.out.println("�鿴���ݿ�����");
		
		DataBaseProperty  dataBaseProperty = null;
		try {
			dataBaseProperty = dataBaseService.showProperty(connectId, databaseName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ScnuResult.build(dataBaseProperty);
		
	}
	
	
	// �޸����ݿ������
    @RequestMapping("/updateDataBase")
    @ResponseBody
    public ScnuResult updateDataBase(String connectId, String databaseName, String characterSetDatabase, String collationDatabase) {
    	
    	System.out.println("�޸����ݿ������");
    	boolean taf = false;
    	try {
			taf = dataBaseService.updateDataBase(connectId, databaseName, characterSetDatabase, collationDatabase);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	if(taf) {
    	    return ScnuResult.build("�޸����ݿ����Գɹ�");
    	}else {
    		return ScnuResult.forbidden("�޸����ݿ�����ʧ��");
    	}
    	
    }
}
