package com.scnu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface DataBaseMapper {
	
	// ��ѯ���ݿ���ַ���
	public List<String> selectCharsets();
	
	// ��ѯ���ݿ���������
    public List<String> selectCollations(@Param("characterSet") String characterSet);
    
    
    
}
