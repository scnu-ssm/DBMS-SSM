package com.zichang.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scnu.dao.DBuserMapper;
import com.scnu.util.ConnectManager;
import com.zichang.bean.DBuser;
import com.zichang.bean.Privilege;

@Service
public class DBuserService {
	
	@Autowired
	ConnectManager connectManager;
	
	public List<String> showUserCol(String connectId) throws Exception{
		SqlSession session = connectManager.getSessionAutoCommitByConnectId(connectId);
		List<String> list = new ArrayList<String>();
		try {
			DBuserMapper mapper = session.getMapper(DBuserMapper.class);
			list = mapper.showUserCol();
		} finally {
			session.close();
		}
		return list;
	}
	
	//�����û�
	public int createUser(String connectId, String username, String host, String pwd) throws Exception{
		SqlSession session = connectManager.getSessionAutoCommitByConnectId(connectId);
		int i = -1;
		try {
			DBuserMapper mapper = session.getMapper(DBuserMapper.class);
			i = mapper.createUser(username, host, pwd);
		} finally {
			session.close();
		}
		return i;
	}
	
	//ɾ���û�
	public int dropUser(String connectId, String username, String host) throws Exception{
		SqlSession session = connectManager.getSessionAutoCommitByConnectId(connectId);
		int i = -1;
		try {
			DBuserMapper mapper = session.getMapper(DBuserMapper.class);
			i = mapper.dropUser(username, host);
		} finally {
			session.close();
		}
		return i;
	}
	
	//��ѯ�����û�
	public List<DBuser> showUsers(String connectId) throws Exception{
		List<DBuser> list = new ArrayList<>();
		SqlSession session = connectManager.getSessionAutoCommitByConnectId(connectId);
		try {
			DBuserMapper mapper = session.getMapper(DBuserMapper.class);
			list = mapper.showUsers();
		} finally {
			session.close();
		}
		return list;
	}
	
	//����username@host��ѯ�û�
	public DBuser selectUserByName(String connectId, String username, String host) throws Exception{
		SqlSession session = connectManager.getSessionAutoCommitByConnectId(connectId);
		try {
			DBuserMapper mapper = session.getMapper(DBuserMapper.class);
			return mapper.selectUserByName(username, host);
		} finally {
			session.close();
		}
	}
	
	//�޸�����
	public int updatePwd(String connectId, String username, String host, String pwd) throws Exception{
		SqlSession session = connectManager.getSessionAutoCommitByConnectId(connectId);
		try {
			DBuserMapper mapper = session.getMapper(DBuserMapper.class);
			return mapper.updatePwd(username, host, pwd);
		} finally {
			session.close();
		}
	}
	
	//��ѯȨ��
	public List<String> showPrivs (String connectId, String username, String host) throws Exception{
		SqlSession session = connectManager.getSessionAutoCommitByConnectId(connectId);
		try {
			DBuserMapper mapper = session.getMapper(DBuserMapper.class);
			return mapper.showPrivs(username, host);
		} finally {
			session.close();
		}
	}
	
	//���Ȩ��
	public int grant(Privilege privilege) throws Exception{
		SqlSession session = connectManager.getSessionAutoCommitByConnectId(privilege.getConnectId());
		int i = -1;
		try {
			DBuserMapper mapper = session.getMapper(DBuserMapper.class);
			i = mapper.grant(privilege);
			mapper.flushPrivs();
		} finally {
			session.close();
		}
		return i;
	}
	
	//����Ȩ��
	public int revoke(Privilege privilege) throws Exception{
		SqlSession session = connectManager.getSessionAutoCommitByConnectId(privilege.getConnectId());
		int i = -1;
		try {
			DBuserMapper mapper = session.getMapper(DBuserMapper.class);
			i = mapper.revoke(privilege);
			mapper.flushPrivs();
		} finally {
			session.close();
		}
		return i;
	}
}
