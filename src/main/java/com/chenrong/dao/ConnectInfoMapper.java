package com.chenrong.dao;

import com.chenrong.bean.ConnectInfo;

public interface ConnectInfoMapper {
	
	// 新建数据库连接
	int insert(ConnectInfo connectInfo);
	
	// 查询数据库连接
	ConnectInfo selectByPrimaryKey(String connectName);
	
	// 修改数据库连接
	int updateByPrimaryKeySelective(ConnectInfo connectInfo);
	
	// 删除数据库连接
	int deleteByPrimaryKey(String connectName);
	
	// 连接名称检测
	int checkConnectName(String connectName);
	
	// 更新后的名称冲突检测
	int checkUpdateConnectName(ConnectInfo connectInfo);

}
