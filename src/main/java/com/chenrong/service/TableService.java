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
	
	// ���������ݼ�¼ɾ��
	public int deleteRecord(TableVO tableVO) throws Exception{
		
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(tableVO.getConnectId());
		try {
		    TableMapper tableMapper = sqlSession.getMapper(TableMapper.class);
		    return tableMapper.deleteRecord(tableVO.getDatabase(), tableVO.getTable(), tableVO.getOldRecord());
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
		    return tableMapper.selectRecords(tableVO.getDatabase(), tableVO.getTable());
		}finally {
			// �ͷ�Session
			if(sqlSession != null) {
				sqlSession.close();
			}
		}

	}
	
	
	// ��ѯһ�����ݼ�¼
	public Map<String, Object> selectRecord(TableVO tableVO) throws Exception{
		
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(tableVO.getConnectId());
		try {
		    TableMapper tableMapper = sqlSession.getMapper(TableMapper.class);
		    return tableMapper.selectRecord(tableVO.getDatabase(), tableVO.getTable(), tableVO.getOldRecord());
		}finally {
			// �ͷ�Session
			if(sqlSession != null) {
				sqlSession.close();
			}
		}
		
	}
	

}
