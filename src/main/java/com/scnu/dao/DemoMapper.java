package com.scnu.dao;

import org.apache.ibatis.annotations.Param;

import com.chenrong.bean.Demo;

public interface DemoMapper {
	
	// ��������
	int insert(@Param("database") String database, @Param("id") String id, @Param("username") String username, @Param("password") String password);
	
	// ��ѯ����
	Demo selectById(@Param("database") String database, @Param("id") String id);

}
