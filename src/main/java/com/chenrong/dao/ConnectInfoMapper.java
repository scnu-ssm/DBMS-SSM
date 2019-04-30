package com.chenrong.dao;

import com.chenrong.bean.ConnectInfo;

public interface ConnectInfoMapper {
	
	// �½����ݿ�����
	int insert(ConnectInfo connectInfo);
	
	// ��ѯ���ݿ�����
	ConnectInfo selectByPrimaryKey(String connectName);
	
	// �޸����ݿ�����
	int updateByPrimaryKeySelective(ConnectInfo connectInfo);
	
	// ɾ�����ݿ�����
	int deleteByPrimaryKey(String connectName);
	
	// �������Ƽ��
	int checkConnectName(String connectName);
	
	// ���º�����Ƴ�ͻ���
	int checkUpdateConnectName(ConnectInfo connectInfo);

}
