package com.chenrong.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chenrong.bean.Const;
import com.chenrong.bean.TableVO;
import com.scnu.dao.TableMapper;
import com.scnu.util.ConnectManager;

@Service
public class TableService {
	
	@Autowired
	ConnectManager connectManager;
	
	// 将数据记录批量删除
	public int deleteRecord(TableVO tableVO) throws Exception{
		
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(tableVO.getConnectId());
		try {
		    TableMapper tableMapper = sqlSession.getMapper(TableMapper.class);
		    Integer affectRow = 0;
		    for(Map<String, Object> oldRecord : tableVO.getOldRecords()) {
		    	affectRow +=tableMapper.deleteRecord(tableVO.getDatabase(), tableVO.getTable(), oldRecord);
		    }
		    return affectRow;
		    
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
		    return tableMapper.selectRecords(tableVO.getDatabase(), tableVO.getTable(), (tableVO.getCurrent() - 1)*Const.SPAN, tableVO.getOrderColumn(), tableVO.getOrderType(), Const.SPAN);
		}finally {
			// 释放Session
			if(sqlSession != null) {
				sqlSession.close();
			}
		}

	}
	
	// 查询数据记录集的数量
	public Integer getRecordsCount(TableVO tableVO) throws Exception{
		
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(tableVO.getConnectId());
		try {
		    TableMapper tableMapper = sqlSession.getMapper(TableMapper.class);
		    return tableMapper.selectAllRecords(tableVO.getDatabase(), tableVO.getTable());
		}finally {
			// 释放Session
			if(sqlSession != null) {
				sqlSession.close();
			}
		}

	}
	
	
	// 按照字段查询记录
	public List<Map<String, Object>> selectRecordsByColumn(String connectId, String database, String table, String columnName, String value, Integer current) throws Exception{
		
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(connectId);
		try {
		    TableMapper tableMapper = sqlSession.getMapper(TableMapper.class);
		    return tableMapper.selectRecordsByColumn(database, table, columnName, value, (current - 1)*Const.SPAN, Const.SPAN);
		}finally {
			// 释放Session
			if(sqlSession != null) {
				sqlSession.close();
			}
		}
		
	}
	
	// 按照字段查询记录总数
	public Integer selectAllRecordsByColumn(String connectId, String database, String table, String columnName, String value) throws Exception{
		
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(connectId);
		try {
		    TableMapper tableMapper = sqlSession.getMapper(TableMapper.class);
		    return tableMapper.selectAllRecordsByColumn(database, table, columnName, value);
		}finally {
			// 释放Session
			if(sqlSession != null) {
				sqlSession.close();
			}
		}
		
	}

}
