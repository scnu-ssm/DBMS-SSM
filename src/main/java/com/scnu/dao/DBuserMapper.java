package com.scnu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zichang.bean.DBuser;
import com.zichang.bean.Privilege;

public interface DBuserMapper {
	
	//��ѯmysql.user��ı�ͷ
	List<String> showUserCol();
	
	//ˢ��Ȩ��
	int flushPrivs();
	
	//�����û�
	int createUser(@Param("username")String username, @Param("host")String host, @Param("pwd")String pwd);
	
	//ɾ���û�
	int dropUser(@Param("username")String username, @Param("host")String host);
	
	//��ѯ�����û�
	List<DBuser> showUsers();
	
	//����username@host��ѯ�û�
	DBuser selectUserByName(@Param("username")String username, @Param("host")String host);
	
	//��ѯȨ��
	List<String> showPrivs(@Param("username")String username, @Param("host")String host);
	
	//�޸�����
	int updatePwd(@Param("username")String username, @Param("host")String host, @Param("pwd")String pwd);
	
	//��Ȩ
	int grant(@Param("privilege")Privilege privilege);
	
	//����Ȩ��
	int revoke(@Param("privilege")Privilege privilege);
	
}
