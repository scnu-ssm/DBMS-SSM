package com.chenrong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.chenrong.bean.ConnectInfo;
import com.chenrong.dao.ConnectInfoMapper;

@Service
public class ConnectInfoService {
	
	@Autowired
	ConnectInfoMapper connectInfoMapper;
	
	// �½�����
	public boolean insert(ConnectInfo connectInfo) {
		
		// �������Ƽ���ͻ
		int num1 = connectInfoMapper.checkConnectName(connectInfo.getConnectName());
		
		// �������Ƽ���ͻ��ʧ��
		if(num1 >= 1)
		return false;
		
		int num2 = connectInfoMapper.insert(connectInfo);
		
		// �½����ӳɹ�
		if(num2 >= 1)
		return true;
		
		// �½�����ʧ��
		return false;
	}
	
	
	// ��ѯ���� �������ӵľ�������
	public ConnectInfo select(String connectName) {
		if(StringUtils.isEmpty(connectName))
			return null;
		return connectInfoMapper.selectByPrimaryKey(connectName);
	}
	
	// ��������
	public boolean update(ConnectInfo connectInfo) {
		// ����Ϊ�գ�ֱ�ӷ��ش���
		if(connectInfo == null)
		return false;
		
		String connectName = connectInfo.getConnectName();
		System.out.println("connectName = " + connectName);
			
		// ���º�����Ƴ�ͻ���
		int num1 = connectInfoMapper.checkUpdateConnectName(connectInfo);
		System.out.println("num1 = " + num1);
		
		// ����ʧ��
		if(num1 >= 1)
		return false;
		
		// ������ͨ���������µ�
		System.out.println("connectId = " + connectInfo.getConnectId());
		System.out.println(connectInfo);
		int num2 = connectInfoMapper.updateByPrimaryKeySelective(connectInfo);
		System.out.println("num2 = " + num2);
		
		// ���³ɹ�
		if(num2 >= 1)
		return true;
		
		// ����ʧ��
		return false;
	}
	
	// ɾ������
	public boolean delete(String connectName) {
		int num = connectInfoMapper.deleteByPrimaryKey(connectName);
		
		// ɾ���ɹ�
		if(num >= 1)
		return true;
		
		// ɾ��ʧ��
		return false;
	}

}
