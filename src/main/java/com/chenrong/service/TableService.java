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
	
	// �����ݼ�¼����ɾ��
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
			// �ͷ�Session
			if(sqlSession != null) {
				sqlSession.close();
			}
		}
		
	}
	
	// �����ݼ�¼����
	public int updateRecord(TableVO tableVO) throws Exception{
		
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(tableVO.getConnectId());
		try {
		    TableMapper tableMapper = sqlSession.getMapper(TableMapper.class);
		    return tableMapper.updateRecord(tableVO.getDatabase(), tableVO.getTable(), tableVO.getNewRecord(), tableVO.getOldRecord());
		}finally {
			// �ͷ�Session
			if(sqlSession != null) {
				sqlSession.close();
			}
		}
		
	}
	
	// �������ݼ�¼
	public int insertRecord(TableVO tableVO) throws Exception{
		
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(tableVO.getConnectId());
		try {
		    TableMapper tableMapper = sqlSession.getMapper(TableMapper.class);
		    return tableMapper.insertRecord(tableVO.getDatabase(), tableVO.getTable(), tableVO.getNewRecord());
		}finally {
			// �ͷ�Session
			if(sqlSession != null) {
				sqlSession.close();
			}
		}
		
	}
	
	
	// ��ѯ���ݼ�¼��
	public List<Map<String, Object>> selectRecords(TableVO tableVO) throws Exception{
		
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(tableVO.getConnectId());
		try {
		    TableMapper tableMapper = sqlSession.getMapper(TableMapper.class);
		    return tableMapper.selectRecords(tableVO.getDatabase(), tableVO.getTable(), (tableVO.getCurrent() - 1)*Const.SPAN, tableVO.getOrderColumn(), tableVO.getOrderType(), Const.SPAN);
		}finally {
			// �ͷ�Session
			if(sqlSession != null) {
				sqlSession.close();
			}
		}

	}
	
	// ��ѯ���ݼ�¼��������
	public Integer getRecordsCount(TableVO tableVO) throws Exception{
		
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(tableVO.getConnectId());
		try {
		    TableMapper tableMapper = sqlSession.getMapper(TableMapper.class);
		    return tableMapper.selectAllRecords(tableVO.getDatabase(), tableVO.getTable());
		}finally {
			// �ͷ�Session
			if(sqlSession != null) {
				sqlSession.close();
			}
		}

	}
	
	
	// �����ֶβ�ѯ��¼
	public List<Map<String, Object>> selectRecordsByColumn(String connectId, String database, String table, String columnName, String value, Integer current) throws Exception{
		
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(connectId);
		try {
		    TableMapper tableMapper = sqlSession.getMapper(TableMapper.class);
		    return tableMapper.selectRecordsByColumn(database, table, columnName, value, (current - 1)*Const.SPAN, Const.SPAN);
		}finally {
			// �ͷ�Session
			if(sqlSession != null) {
				sqlSession.close();
			}
		}
		
	}
	
	// �����ֶβ�ѯ��¼����
	public Integer selectAllRecordsByColumn(String connectId, String database, String table, String columnName, String value) throws Exception{
		
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(connectId);
		try {
		    TableMapper tableMapper = sqlSession.getMapper(TableMapper.class);
		    return tableMapper.selectAllRecordsByColumn(database, table, columnName, value);
		}finally {
			// �ͷ�Session
			if(sqlSession != null) {
				sqlSession.close();
			}
		}
		
	}

}
