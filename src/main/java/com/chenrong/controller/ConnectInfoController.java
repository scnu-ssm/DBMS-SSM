package com.chenrong.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chenrong.bean.ConnectInfo;
import com.chenrong.bean.Msg;
import com.chenrong.service.ConnectInfoService;

@Controller
@RequestMapping("/connectInfo")
public class ConnectInfoController {
	
	@Autowired
	ConnectInfoService connectInfoService;
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	// �½�������Ϣ
	public Msg createConnect(@Param("connectInfo") ConnectInfo connectInfo) {
		
		System.out.println("�ҽ����½����ӷ�������");
		
		boolean taf = false;
		
		taf = connectInfoService.insert(connectInfo);
		
		// �½�������Ϣ�ɹ�
		if(taf)
		return Msg.connectInfoInsertSuccess();
		
		// �½�����ʧ��
		return Msg.connectInfoInsertFailure();
	}
	
	@RequestMapping(value = "/select", method = RequestMethod.POST)
	@ResponseBody
	// ����������Ϣ
	// ��Ҫͨ�� connect_Name ���Ҽ�¼
	public Msg selectConnect(@Param("connectInfo") ConnectInfo connectInfo) {
	
		String conenctName = connectInfo.getConnectName();
		
		// �жϿյ����
		if(StringUtils.isEmpty(conenctName))
		return null;
		
		// ��ȡ������Ϣ������
		ConnectInfo selectConnectInfo = connectInfoService.select(conenctName);
		
		// ����������Ϣʧ��
		if(selectConnectInfo == null)
		return Msg.connectInfoSelectFailure();
		
		System.out.println(selectConnectInfo);
		
		// ����������Ϣ�ɹ�
		return Msg.connectInfoSelectSuccess();
		
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	// ����������Ϣ
	// һ��Ҫ��connect_Id������ǰ̨��Ҫ�����
	public Msg updateConnect(@Param("connectInfo") ConnectInfo connectInfo) {
		
		// connect_Id �� connect_Name�ļ��
		if(connectInfo.getConnectId() == 0 || connectInfo.getConnectName() == null)
		return null;
		
		boolean taf = false;
		
		taf = connectInfoService.update(connectInfo);
		
		// ����������Ϣ�ɹ�
		if(taf)
		return Msg.connectInfoUpdateSuccess();
		
		// ����������Ϣʧ��, ��������ͻ
	    return Msg.connectInfoUpdateFailure();
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	// ��Ҫͨ�� connect_Name ɾ����¼
	public Msg deleteConnect(@Param("connectInfo") ConnectInfo connectInfo) {
		String connectName = connectInfo.getConnectName();
		
		boolean taf = false;
		
		taf = connectInfoService.delete(connectName);
		
		// ɾ�����ӳɹ�
		if(taf)
		return Msg.connectInfoDeleteSuccess();
		
		// ɾ������ʧ��
		return Msg.connectInfoDeleteFailure();
	}

}
