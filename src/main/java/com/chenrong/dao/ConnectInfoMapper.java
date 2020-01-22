package com.chenrong.dao;

import java.util.List;

import com.chenrong.bean.ConnectInfo;

public interface ConnectInfoMapper {
	
	// 新建数据库连接
	int insert(ConnectInfo connectInfo);
	
	// 通过userId查询数据库连接
	List<ConnectInfo> selectByUserId(String userId);
	
	// 通过connectId查询数据库连接
    ConnectInfo selectByConnectId(String connectId);
	
	// 更新数据库连接
	int updateByPrimaryKeySelective(ConnectInfo connectInfo);
	
	// 删除数据库连接
	int deleteByPrimaryKey(String connectId);
	
	// 根据userId查询连接
	List<ConnectInfo> selectConnectByUserId(String userId);

}
