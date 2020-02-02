package com.chenrong.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chenrong.bean.TableVO;
import com.scnu.dao.TableMapper;
import com.scnu.util.ConnectManager;

@Service
public class TableService {
	
	@Autowired
	ConnectManager connectManager;
	
	// 将单条数据记录删除
	public int deleteRecord(TableVO tableVO) throws Exception{
		
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(tableVO.getConnectId());
		try {
		    TableMapper tableMapper = sqlSession.getMapper(TableMapper.class);
		    return tableMapper.deleteRecord(tableVO.getDatabase(), tableVO.getTable(), tableVO.getOldRecord());
		}finally {
			// 释放Session
			if(sqlSession != null) {
				sqlSession.close();
			}
		}
		
	}
	
	// 将数据记录更新
	public int updateRecord(TableVO tableVO) throws Exception{
		
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(tableVO.getConnectId());
		try {
		    TableMapper tableMapper = sqlSession.getMapper(TableMapper.class);
		    return tableMapper.updateRecord(tableVO.getDatabase(), tableVO.getTable(), tableVO.getNewRecord(), tableVO.getOldRecord());
		}finally {
			// 释放Session
			if(sqlSession != null) {
				sqlSession.close();
			}
		}
		
	}
	
	// 插入数据记录
	public int insertRecord(TableVO tableVO) throws Exception{
		
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(tableVO.getConnectId());
		try {
		    TableMapper tableMapper = sqlSession.getMapper(TableMapper.class);
		    return tableMapper.insertRecord(tableVO.getDatabase(), tableVO.getTable(), tableVO.getNewRecord());
		}finally {
			// 释放Session
			if(sqlSession != null) {
				sqlSession.close();
			}
		}
		
	}
	
	
	// 查询数据记录集
	public List<Map<String, Object>> selectRecords(TableVO tableVO) throws Exception{
		
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(tableVO.getConnectId());
		try {
		    TableMapper tableMapper = sqlSession.getMapper(TableMapper.class);
		    return tableMapper.selectRecords(tableVO.getDatabase(), tableVO.getTable());
		}finally {
			// 释放Session
			if(sqlSession != null) {
				sqlSession.close();
			}
		}

	}
	
	
	// 查询一个数据记录
	public Map<String, Object> selectRecord(TableVO tableVO) throws Exception{
		
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(tableVO.getConnectId());
		try {
		    TableMapper tableMapper = sqlSession.getMapper(TableMapper.class);
		    return tableMapper.selectRecord(tableVO.getDatabase(), tableVO.getTable(), tableVO.getOldRecord());
		}finally {
			// 释放Session
			if(sqlSession != null) {
				sqlSession.close();
			}
		}
		
	}
	

}
