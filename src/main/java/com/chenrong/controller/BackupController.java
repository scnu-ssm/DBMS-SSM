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
	
	// ���ݿ����� ����sql
	@RequestMapping(value = "/backup", method=RequestMethod.POST)
	@ResponseBody
	public ScnuResult BackUp(String conenctId, String database, String dest) {
		
		boolean taf = backupService.BackUp(conenctId, database, dest);
		
		if(taf) {
			return ScnuResult.build("ת��SQL�ļ��ɹ�");
		}else {
			return ScnuResult.forbidden("ת��SQL�ļ�ʧ��");
		}
		
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
		}
		if(status.equals(-1)) {
			return ScnuResult.forbidden("����������");
		}else if(status.equals(0)) {
			return ScnuResult.forbidden("�����쳣");
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
 
}
