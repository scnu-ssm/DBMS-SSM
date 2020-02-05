package com.scnu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface DataBaseMapper {
	
	// 查询数据库的字符集
	public List<String> selectCharsets();
	
	// 查询数据库的排序规则
    public List<String> selectCollations(@Param("characterSet") String characterSet);
    
    
    
}
