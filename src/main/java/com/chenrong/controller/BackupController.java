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
	
	// ���ݿ����� ����sql
	@RequestMapping(value = "/backup", method=RequestMethod.POST)
	@ResponseBody
	public ScnuResult BackUp(String connectId, String database, @RequestParam("tables") List<String> tables, String dest, boolean isOnlyStructor) {
		
		if(tables == null || tables.size() == 0) {
			return ScnuResult.forbidden("�������κβ���");
		}
		
		try {
			backupService.Backup(connectId, database, tables, dest, isOnlyStructor);
		} catch (Exception e) {
			e.printStackTrace();
			return ScnuResult.forbidden("ת��SQL�ļ�ʧ��");
		}
		
		return ScnuResult.build("ת��SQL�ļ��ɹ�");
		
	}
	
	
	// ��ԭ������ ����sql
	@RequestMapping(value = "/recovery", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult Recovery(String conenctId, String database, String src) {
		
		Integer status = 0;
		try {
			status = backupService.Recovery(conenctId, database, src);
		} catch (Exception e) {
			e.printStackTrace();
			return ScnuResult.forbidden("�����쳣");
		}
		if(status.equals(-1)) {
			return ScnuResult.forbidden("����������");
		}else {
			return ScnuResult.build("��ԭ�ɹ�");
		}
		
	}
	
    // �������ݵ�csv�ļ���
    @RequestMapping(value = "/leadingOutCSV", method = RequestMethod.POST)
    @ResponseBody
    public ScnuResult leadingOutCSV(ConnectVO connectVO, String dest) {

    	if(connectVO == null || connectVO.getConnectId() == null || 
    	   connectVO.getDatabase() == null || connectVO.getTable() == null || dest == null) {
    		    return ScnuResult.forbidden("��������ʧ��");
    	}
    	try {
          backupService.leadingOutCSV(connectVO, dest);
		} catch (Exception e) {
			e.printStackTrace();
			return ScnuResult.forbidden("�����������쳣����");
		}
    	return ScnuResult.build("�������ݳɹ�");
    	
    }
    
    // ���ݿ������ݴ���ģ�鷽ʽ2,���ش���ɹ���ʧ�ܵ��������Ϊ��ʱ
    @RequestMapping(value = "dataTransfer2", method = RequestMethod.POST)
    @ResponseBody
    public ScnuResult DataTransfer2(String srcConnectId, String srcDatabase, @RequestParam("tables") List<String> tables, String destconnectId, String destDatabase, boolean isOnlyStructor) {
    	// �����������ݱ�����ʧ��
    	if(tables == null || tables.size() == 0) {
    		  return ScnuResult.forbidden("û�н����κβ���");
    	}
    	List<String> success = new ArrayList();
    	List<String> failure = new ArrayList();
    	// ���������ʧ�ܷŵ�ʧ�ܵļ��ϣ��ɹ��ŵ��ɹ��ļ���
    	for(String table : tables) {
    		try {
    			// ����
        		String path = backupService.BackupTable(srcConnectId, srcDatabase, table, isOnlyStructor);
        		// ��������
        		backupService.Recovery(destconnectId, destDatabase, path);
        		// ɾ����ʱ���ļ�
        		File file = new File(path);
        		boolean isDeleteFile = file.delete();
        		if(isDeleteFile) {
        			System.out.println(table + "��ʱ�ļ�ɾ���ɹ�");
        		}else {
        			// ǿ��ɾ����ʱ�ļ�
        			for(int i = 0; i < 10 && !file.delete(); i++) {
        				System.gc();
        				System.out.println("��" + (i + 1) + "�ν��г���ɾ����ʱ�ļ�" + table);
        			}
        		}
    		}catch(Exception e) {
    			failure.add(table);
    			System.out.println("���ݴ������");
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
    
    // ���ݿ������ݴ���ģ�鷽ʽ����ʱ��
    @RequestMapping(value = "dataTransfer", method = RequestMethod.POST)
    @ResponseBody
    public ScnuResult DataTransfer(String srcConnectId, String srcDatabase, @RequestParam("tables") List<String> tables, String destconnectId, String destDatabase, boolean isOnlyStructor) {
    	// �����������ݱ�����ʧ��
    	if(tables == null || tables.size() == 0) {
    		  return ScnuResult.forbidden("û�н����κβ���");
    	}

    	String path = BackupService.TempPath + GenerateIDUtil.getUUID32() + BackupService.suffix;
    	try {
    	// �����ļ�
    	BackupService.generateFile(path);
    	// ��������
        backupService.Backup(srcConnectId, srcDatabase, tables, path, isOnlyStructor);
        // ��������
        backupService.Recovery(destconnectId, destDatabase, path);
        // ɾ����ʱ�ļ�
        File file = new File(path);
        boolean isDeleteFile = file.delete();
        if(isDeleteFile) {
        	System.out.println("��ʱ�ļ�ɾ���ɹ�");
        }else {
        	System.out.println("��ʱ�ļ�ɾ��ʧ��");
        	 // ǿ��ɾ����ʱ�ļ�
        	for(int i = 0; i < 10 && !file.delete(); i++) {
        		    System.gc();
        			System.out.println("��" + (i + 1) + "�����³���ɾ����ʱ�ļ�");
        	}
        }
    	}catch(Exception e) {
    		e.printStackTrace();
    		return ScnuResult.forbidden("���ݴ���ʧ��");
    	}
    		 
    	return ScnuResult.build("���ݴ���ɹ�");
    }
    
    
 
}
