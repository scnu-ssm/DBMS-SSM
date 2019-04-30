package com.chenrong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.chenrong.bean.ConnectInfo;
import com.chenrong.dao.ConnectInfoMapper;

@Service
public class ConnectInfoService {
	
	@Autowired
	ConnectInfoMapper connectInfoMapper;
	
	// 新建连接
	public boolean insert(ConnectInfo connectInfo) {
		
		// 连接名称检测冲突
		int num1 = connectInfoMapper.checkConnectName(connectInfo.getConnectName());
		
		// 连接名称检测冲突，失败
		if(num1 >= 1)
		return false;
		
		int num2 = connectInfoMapper.insert(connectInfo);
		
		// 新建连接成功
		if(num2 >= 1)
		return true;
		
		// 新建连接失败
		return false;
	}
	
	
	// 查询连接 返回连接的具体内容
	public ConnectInfo select(String connectName) {
		if(StringUtils.isEmpty(connectName))
			return null;
		return connectInfoMapper.selectByPrimaryKey(connectName);
	}
	
	// 更新连接
	public boolean update(ConnectInfo connectInfo) {
		// 假若为空，直接返回错误
		if(connectInfo == null)
		return false;
		
		String connectName = connectInfo.getConnectName();
		System.out.println("connectName = " + connectName);
			
		// 更新后的名称冲突检测
		int num1 = connectInfoMapper.checkUpdateConnectName(connectInfo);
		System.out.println("num1 = " + num1);
		
		// 更新失败
		if(num1 >= 1)
		return false;
		
		// 更新是通过主键更新的
		System.out.println("connectId = " + connectInfo.getConnectId());
		System.out.println(connectInfo);
		int num2 = connectInfoMapper.updateByPrimaryKeySelective(connectInfo);
		System.out.println("num2 = " + num2);
		
		// 更新成功
		if(num2 >= 1)
		return true;
		
		// 更新失败
		return false;
	}
	
	// 删除连接
	public boolean delete(String connectName) {
		int num = connectInfoMapper.deleteByPrimaryKey(connectName);
		
		// 删除成功
		if(num >= 1)
		return true;
		
		// 删除失败
		return false;
	}

}
