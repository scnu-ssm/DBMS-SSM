package com.chenrong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.chenrong.bean.ConnectInfo;
import com.chenrong.dao.ConnectInfoMapper;
import com.scnu.util.ConnectManager;

@Service
public class ConnectInfoService {
	
	@Autowired
	ConnectInfoMapper connectInfoMapper;
	
	@Autowired
	ConnectManager connectManager;
	
	// �½�����
	public boolean insert(ConnectInfo connectInfo) {
		
		int num = connectInfoMapper.insert(connectInfo);
		
		// �½����ӳɹ�
		if(num >= 1)
		return true;
		
		// �½�����ʧ��
		return false;
	}
	
	
	// ����connectId��ѯ����
	public ConnectInfo selectByConnectId(String connectId) {
		if(StringUtils.isEmpty(connectId))
			return null;
		return connectInfoMapper.selectByConnectId(connectId);
	}
	
	// ����userId��ѯ����
	public List<ConnectInfo> selectByUserId(String userId){
		if(StringUtils.isEmpty(userId))
			return null;
		return connectInfoMapper.selectByUserId(userId);
	}
	
	// ��������
	public boolean update(ConnectInfo connectInfo) {
		// ����Ϊ�գ�ֱ�ӷ��ش���
		if(connectInfo == null)
		return false;
		int num = connectInfoMapper.updateByPrimaryKeySelective(connectInfo);
		// ���³ɹ���ɾ�����ӳ�ԭ��������
		if(num >= 1) {
		   connectManager.deleteConnect(connectInfo.getConnectId());
		   System.out.println("ɾ��ԭ�������ӳɹ�!");
		   return true;
		}
		// ����ʧ��
		return false;
	}
	
	// ����connectɾ������
	public boolean delete(String connectId) {
		int num = connectInfoMapper.deleteByPrimaryKey(connectId);
		// ɾ���ɹ�
		if(num >= 1)
		return true;
		// ɾ��ʧ��
		return false;
	}
	
	// ����userId��ѯ����
	public List<ConnectInfo> selectConnectByUserId(String userId) {
		   return connectInfoMapper.selectByUserId(userId);
	}
	
	// ����connectId�ر�����,������������
	public String closeConnect(String connectId) {
		connectManager.deleteConnect(connectId);
		ConnectInfo connectInfo = selectByConnectId(connectId);
		return connectInfo == null ? null : connectInfo.getConnectName();
	}

}
