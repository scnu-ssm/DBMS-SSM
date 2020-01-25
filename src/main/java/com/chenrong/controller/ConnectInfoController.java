package com.chenrong.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chenrong.bean.ConnectInfo;
import com.chenrong.bean.ScnuResult;
import com.chenrong.service.ConnectInfoService;
import com.chenrong.util.CookieUtil;
import com.chenrong.util.GenerateIDUtil;

@Controller
@RequestMapping("/connectInfo")
public class ConnectInfoController {
	
	@Autowired
	ConnectInfoService connectInfoService;
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	// �½�������Ϣ
	public ScnuResult createConnect(@Param("connectInfo") ConnectInfo connectInfo, HttpServletRequest request) {
		
		System.out.println("�½�����");
		String userId = CookieUtil.getUserID(request);
		boolean taf = false;
		connectInfo.setConnectId(GenerateIDUtil.getUUID32());
		connectInfo.setUserId(userId);
		System.out.println("connectInfo  :  " + connectInfo.toString());
		taf = connectInfoService.insert(connectInfo);
		
		// �½�������Ϣ�ɹ�
		if(taf) {
		   return ScnuResult.connectInfoInsertSuccess();
		}
		// �½�����ʧ��
		return ScnuResult.connectInfoInsertFailure();
	}
	
	@RequestMapping(value = "/selectByConnectId", method = RequestMethod.GET)
	@ResponseBody
	// ����������Ϣ
	// ��Ҫͨ�� conenctId ���Ҽ�¼
	public ScnuResult selectConnect(String connectId) {
		
		// �жϿյ����
		if(StringUtils.isEmpty(connectId)) {
		   return null;
		}
		
		// ��ȡ������Ϣ������
		ConnectInfo selectConnectInfo = connectInfoService.selectByConnectId(connectId);
		
		// ����������Ϣʧ��
		if(selectConnectInfo == null) {
		   return ScnuResult.connectInfoSelectFailure();
		}
		System.out.println(selectConnectInfo.toString());
		
		// ��UserId����Ϣ�ÿ�
		selectConnectInfo.setUserId(null);
		
		// ����������Ϣ�ɹ�
		return ScnuResult.build(selectConnectInfo);
		
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	// ����������Ϣ
	// һ��Ҫ��connect_Id������ǰ̨��Ҫ�����
	public ScnuResult updateConnect(@Param("connectInfo") ConnectInfo connectInfo) {
		
		// connect_Id���
		if(connectInfo.getConnectId() == null) {
		   return null;
		}
		boolean taf = false;
		taf = connectInfoService.update(connectInfo);
		// ����������Ϣ�ɹ�
		if(taf) {
		    return ScnuResult.connectInfoUpdateSuccess();
		}
		
		// ����������Ϣʧ��
	    return ScnuResult.connectInfoUpdateFailure();
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	// ��Ҫͨ�� connect_Id ɾ����¼
	public ScnuResult deleteConnect(String connectId) {
		
		boolean taf = false;
		taf = connectInfoService.delete(connectId);
		// ɾ�����ӳɹ�
		if(taf) {
		    return ScnuResult.connectInfoDeleteSuccess();
		}
		// ɾ������ʧ��
		return ScnuResult.connectInfoDeleteFailure();
		
	}
	
	// ͨ��UserId��ѯ����
	@RequestMapping(value = "/selectByUserId", method = RequestMethod.GET)
	@ResponseBody
	public ScnuResult selectConnectByUserId(HttpServletRequest request) {
		String userId = CookieUtil.getUserID(request);
		List<ConnectInfo> list = connectInfoService.selectByUserId(userId);
		// ��UserId����Ϣ�ÿ�
		for(ConnectInfo connect : list) {
			connect.setUserId(null);
		}
		return ScnuResult.build(list);
	}

}
