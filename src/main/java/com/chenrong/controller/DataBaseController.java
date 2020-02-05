package com.chenrong.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	@RequestMapping(value = "/createDateBase",  method = RequestMethod.POST)
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
			return ScnuResult.build(databaseName);
		}else {
			return ScnuResult.build("�������ݿ�: " + databaseName + " ʧ��");
		}
	}
	
	// ɾ�����ݿ�
	@RequestMapping(value = "/deleteDateBase", method = RequestMethod.POST)
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
    @RequestMapping(value = "/updateDataBase",  method = RequestMethod.POST)
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
    
    // �鿴���ݿ���ַ���
    @RequestMapping("/selectCharacterSet")
    @ResponseBody
    public ScnuResult selectCharacterSet(String connectId) {
    	
    	if(connectId == null) {
    		return ScnuResult.forbidden("connectId ������");
    	}
    	try {
    	    List characterSet = dataBaseService.getCharacterSets(connectId);
    	    if(characterSet == null || characterSet.size() == 0) {
    	    	   return ScnuResult.forbidden("�ַ�����ѯʧ��");
    	    }
    	    return ScnuResult.build(characterSet);
    	}catch(Exception e) {
    		e.printStackTrace();
    		return ScnuResult.forbidden("�������쳣����ѯʧ��");
    	}
    	
    }
    
    // �鿴���ݿ������
    @RequestMapping("/selectCollations")
    @ResponseBody
    public ScnuResult selectCollations(String connectId, String characterSet) {
    	
    	   if(connectId == null || characterSet == null) {
    		     return ScnuResult.forbidden("����ȱʧ");
    	   }
       	   try {
    	       List collations = dataBaseService.getCollations(connectId, characterSet);
    	       if(collations == null || collations.size() == 0) {
    	    	   return ScnuResult.forbidden("��������ѯʧ��");
    	        }
    	        return ScnuResult.build(collations);
    	   }catch(Exception e) {
    		   e.printStackTrace();
    		   return ScnuResult.forbidden("�������쳣����ѯʧ��");
    	   }
       	   
    }
    
}
