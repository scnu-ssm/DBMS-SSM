package com.chenrong.dao;

import java.util.List;

import com.chenrong.bean.ConnectInfo;

public interface ConnectInfoMapper {
	
	// �½����ݿ�����
	int insert(ConnectInfo connectInfo);
	
	// ͨ��userId��ѯ����
	List<ConnectInfo> selectByUserId(String userId);
	
	// ͨ��connectId��ѯ���ݿ�����
    ConnectInfo selectByConnectId(String connectId);
	
	// �������ݿ�����
	int updateByPrimaryKeySelective(ConnectInfo connectInfo);
	
	// ɾ�����ݿ�����
	int deleteByPrimaryKey(String connectId);

}
