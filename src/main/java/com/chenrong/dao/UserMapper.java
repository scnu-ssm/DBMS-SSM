package com.chenrong.dao;

import com.chenrong.bean.User;

public interface UserMapper {
	
	// ע��
	int insert(User user);
	
	// ͨ����������
	User selectByPrimaryKey(Integer id);
	
	// ͨ�������ֶ���������������
	User selectBySelective(User user);
	
	// ��¼
	int login(User user);
	
	// ������Ϣ����
	int updateByPrimaryKeySelective(User user);
	
	// �û������
	int checkUsername(String username);
	
	// ������
	int checkEmail(String email);
	
	// ��ѯ�û� By username
	User selectByUsername(String username);
	
	// ��ѯ�û� By userId
	User selectByUserId(String userId);

}
