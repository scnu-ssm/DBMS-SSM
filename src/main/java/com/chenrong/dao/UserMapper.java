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
	
	// �绰���
	int checkTelephone(String telephone);
	

}
