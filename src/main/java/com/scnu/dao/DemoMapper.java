package com.scnu.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface DemoMapper {
	
	// 插入数据
	int insert(@Param("database") String database, @Param("id") String id, @Param("username") String username, @Param("password") String password);
	
	// 查询数据
	List<Map> selectById(@Param("database") String database, @Param("id") String id);

}
