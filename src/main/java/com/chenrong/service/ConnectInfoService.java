package com.chenrong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.chenrong.bean.ConnectInfo;
import com.chenrong.dao.ConnectInfoMapper;
import com.scnu.util.ConnectManager;

@Service
public class ConnectInfoService {
	
	@Autowired
	ConnectInfoMapper connectInfoMapper;
	
	@Autowired
	ConnectManager connectManager;
	
	// 新建连接
	public boolean insert(ConnectInfo connectInfo) {
		
		int num = connectInfoMapper.insert(connectInfo);
		
		// 新建连接成功
		if(num >= 1)
		return true;
		
		// 新建连接失败
		return false;
	}
	
	
	// 根据connectId查询连接
	public ConnectInfo selectByConnectId(String connectId) {
		if(StringUtils.isEmpty(connectId))
			return null;
		return connectInfoMapper.selectByConnectId(connectId);
	}
	
	// 根据userId查询连接
	public List<ConnectInfo> selectByUserId(String userId){
		if(StringUtils.isEmpty(userId))
			return null;
		return connectInfoMapper.selectByUserId(userId);
	}
	
	// 更新连接
	public boolean update(ConnectInfo connectInfo) {
		// 假若为空，直接返回错误
		if(connectInfo == null)
		return false;
		int num = connectInfoMapper.updateByPrimaryKeySelective(connectInfo);
		// 更新成功，删除连接池原来的连接
		if(num >= 1) {
		   connectManager.deleteConnect(connectInfo.getConnectId());
		   System.out.println("删除原来的连接成功!");
		   return true;
		}
		// 更新失败
		return false;
	}
	
	// 根据connect删除连接
	public boolean delete(String connectId) {
		int num = connectInfoMapper.deleteByPrimaryKey(connectId);
		// 删除成功
		if(num >= 1)
		return true;
		// 删除失败
		return false;
	}
	
	// 根据userId查询连接
	public List<ConnectInfo> selectConnectByUserId(String userId) {
		   return connectInfoMapper.selectByUserId(userId);
	}
	
	// 根据connectId关闭连接,返回连接名字
	public String closeConnect(String connectId) {
		connectManager.deleteConnect(connectId);
		ConnectInfo connectInfo = selectByConnectId(connectId);
		return connectInfo == null ? null : connectInfo.getConnectName();
	}

}
