package com.zichang.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chenrong.bean.ScnuResult;
import com.mysql.cj.Session;
import com.scnu.dao.TableInfoMapper;
import com.scnu.util.ConnectManager;
import com.zichang.bean.Fields;
import com.zichang.bean.ForeignKey;
import com.zichang.bean.Indexes;
import com.zichang.util.TableInfoTool;

@Service
public class TableInfoService {
	
	@Autowired
	ConnectManager connectManager;
	//�������ݿ����б���
	public List<String> showTables(String connectId, String databaseName) throws Exception{
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(connectId);
		try {
			TableInfoMapper mapper = sqlSession.getMapper(TableInfoMapper.class);
			return mapper.showTables(databaseName);
		} finally {
			sqlSession.close();
		}
	}
	//ɾ����
	public int dropTable(String connectId, String database, String table) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		map.put("database", database);
		map.put("table", table);
		
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(connectId);
		try {
			TableInfoMapper mapper = sqlSession.getMapper(TableInfoMapper.class);
			int num = mapper.dropTable(map);
//			System.out.println("num:"+num);
			return num;
		} finally {
			sqlSession.close();
		}
	}
	//��ձ�
	public int truncateTable(String connectId, String database, String table) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		map.put("database", database);
		map.put("table", table);
		
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(connectId);
		try {
			TableInfoMapper mapper = sqlSession.getMapper(TableInfoMapper.class);
			return mapper.truncateTable(map);
		} finally {
			sqlSession.close();
		}
	}
	
	//������
	public int createTable(String connectId, String database, String table, List<Fields> fields, List<ForeignKey> foreignKeys) throws Exception{
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(connectId);
		try {
			TableInfoMapper mapper = sqlSession.getMapper(TableInfoMapper.class);
			return mapper.createTable(database, table, fields, foreignKeys);
		} finally {
			sqlSession.close();
		}
	}
	
	//��ѯ���ֶ�
	public List<String> showColumns(String connectId, String database, String table) throws Exception{
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(connectId);
		List<String> list = new ArrayList<String>();
		try {
			TableInfoMapper mapper = sqlSession.getMapper(TableInfoMapper.class);
			list = mapper.showColumns(database, table);
		} finally {
			sqlSession.close();
		}
		return list;
	}
	
	//�����ֶ�
	public ScnuResult insertField(String connectId, String database, String table, Fields field) throws Exception{
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(connectId);
		
		//�ж��ֶ��Ƿ��Ѿ�����
		try {
			TableInfoMapper mapper = sqlSession.getMapper(TableInfoMapper.class);
			List<String> list = mapper.showColumns(database, table);
			int isExistField = 0;
			if(!list.isEmpty()) {
				for(int i = 0; i<list.size(); i++) {
					String str = list.get(i);
					if(str.equals(field.getName())) {
						isExistField = 1;
						break;
					}
				}
			}
			if(isExistField == 1) {
				return ScnuResult.buildFalure("�ֶ��Ѵ���");
				
			}else {
				mapper.insertField(database, table, field);
				return ScnuResult.build("�����ֶγɹ�");
			}
		} finally {
			sqlSession.close();
		}
	}
	
	//ɾ���ֶ�
	public int dropField(String connectId, String database, String table, String field) throws Exception{
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(connectId);
		try {
			TableInfoMapper mapper = sqlSession.getMapper(TableInfoMapper.class);
			return mapper.dropField(database, table, field);
		} finally {
			sqlSession.close();
		}
	}
	
	//�޸��ֶ�
	public int changeField(String connectId, String database, String table, String fieldName, Fields field) throws Exception{
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(connectId);
		try {
			TableInfoMapper mapper = sqlSession.getMapper(TableInfoMapper.class);
			return mapper.changeField(database, table, fieldName, field);
		} finally {
			sqlSession.close();
		}
	}
	
	//��ѯ��ṹ
	public List<Map<String, Object>> showTableMsg(String connectId, String database, String table) throws Exception{
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(connectId);
		try {
			TableInfoMapper mapper = sqlSession.getMapper(TableInfoMapper.class);
			List<Map<String, Object>> list = mapper.showTableMsg(database, table);
			return list;
		} finally {
			sqlSession.close();
		}
	}
	
	//��������
	public int renameTable(String connectId , String database, String table, String rename) throws Exception{
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(connectId);
		try {
			TableInfoMapper mapper = sqlSession.getMapper(TableInfoMapper.class);
			return mapper.renameTable(database, table, rename);
		} finally {
			sqlSession.close();
		}
	}
	
	//��ѯ���
	public List<ForeignKey> allfk(String connectId, String database, String table) throws Exception{
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(connectId);
		try {
			TableInfoMapper mapper = sqlSession.getMapper(TableInfoMapper.class);
			return mapper.allfk(database, table);
		} finally {
			sqlSession.close();
		}
	}
	
	//ɾ�����
	public int deletefk(String connectId, String database, String table, String fname) throws Exception{
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(connectId);
		try {
			TableInfoMapper mapper = sqlSession.getMapper(TableInfoMapper.class);
			return mapper.deletefk(database, table, fname);
		} finally {
			sqlSession.close();
		}
	}
	
	//������
	public int insertfk(String connectId, String database, String table, ForeignKey fk) throws Exception{
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(connectId);
		try {
			TableInfoMapper mapper = sqlSession.getMapper(TableInfoMapper.class);
			return mapper.insertfk(database, table, fk);
		} finally {
			sqlSession.close();
		}
	}
	
	//��ѯ����
	public List<String> selectpk(String connectId, String database, String table) throws Exception{
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(connectId);
		try {
			TableInfoMapper mapper = sqlSession.getMapper(TableInfoMapper.class);
			return mapper.selectpk(database, table);
		} finally {
			sqlSession.close();
		}
	}
	
	//��������
	public int setpk(String connectId, String database, String table, String field) throws Exception{
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(connectId);
		try {
			TableInfoMapper mapper = sqlSession.getMapper(TableInfoMapper.class);
			return mapper.setpk(database, table, field);
		} finally {
			sqlSession.close();
		}
	}
	
	public int deletepk(String connectId, String database, String table) throws Exception{
		SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(connectId);
		try {
			TableInfoMapper mapper = sqlSession.getMapper(TableInfoMapper.class);
			return mapper.deletepk(database, table);
		} finally {
			sqlSession.close();
		}
	}
}
