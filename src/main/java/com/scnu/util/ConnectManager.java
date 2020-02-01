package com.scnu.util;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ��ȡ���ӵĺ����࣬ConnectManagerΪ�������࣬��Ҫע���̰߳�ȫ
 * @author chenrong
 */
@Component
public class ConnectManager {
	
	// Key�Ĺ���ʱ��, 20����
	public final static Long expire = 20*60*1000L;
	//public final static Long expire = 60*1000L;
	
	@Autowired
	JDBCMyBatisUtil jDBCMyBatisUtil;
	
	private Map<String, SqlSessionFactory> pool = new ConcurrentHashMap();
	
	// ��¼���ӹ��ڵ�ʱ�䣬
	private Map<String, Long> expiresPool = new ConcurrentHashMap();
	
	// ͨ��connectId��ȡSqlSessionFactory
	public  SqlSessionFactory getSqlSessionFactory(String connectId) throws Exception{
		    expiresPool.put(connectId, System.currentTimeMillis() + expire);
		    if(pool.containsKey(connectId)) {
		    	return pool.get(connectId);
		    }else {
		    	SqlSessionFactory sqlSessionFactory = jDBCMyBatisUtil.getSqlSessionFactory(connectId);
		    	pool.put(connectId, sqlSessionFactory);
		    	return sqlSessionFactory;
		    }
	}
	
	// ͨ��connectIdɾ��SqlSessionFactory
	public SqlSessionFactory deleteConnect(String connectId) {
		   SqlSessionFactory sqlSessionFactory = pool.remove(connectId);
		   expiresPool.remove(connectId);
		   return sqlSessionFactory;
	}
	
	// ��ȡ�ܹ�ִ��SQL����SqlSession����SqlSessionĬ���Զ��ύ����
	public SqlSession getSessionAutoCommitByConnectId(String connectId) throws Exception {
		   SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(connectId);
		   if(sqlSessionFactory == null) {
			   throw new Exception("������ SqlSessionFactory !!!");
		   }
		   // ���������Զ��ύ
		   return sqlSessionFactory.openSession(true);
	}
	
	// ��ȡ�ܹ�ִ��SQL����SqlSession����SqlSession����Ҫcommit�ύ����
	public SqlSession getSessionByConnectId(String connectId) throws Exception{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(connectId);
		   if(sqlSessionFactory == null) {
			   throw new Exception("������ SqlSessionFactory !!!");
		   }
		   // ���������Զ��ύ
		   return sqlSessionFactory.openSession();
	}
	
	// ��ȡ�ӿڶ�Ӧ��Mapper, ����Ϊ connectId�� T.class, Ĭ���Զ��ύ����
	public <T> T getMapper(String connectId, Class<T> type) throws Exception{
		   return getMapper(connectId, type, true);
	}
	
	// ��ȡ�ӿڶ�Ӧ��Mapper, ����Ϊ connectId�� T.class
	public <T> T getMapper(String connectId, Class<T> type, boolean isAutoCommit) throws Exception{
		   SqlSession sqlSession = null;
		   if(isAutoCommit) {
			   sqlSession = getSessionAutoCommitByConnectId(connectId);
		   }else {
			   sqlSession = getSessionByConnectId(connectId);
		   }
		   return sqlSession.getMapper(type);
	}
	
	// �õ�expiresPool��������
	public Map geExpiresPoolMap() {
		   return expiresPool;
	}
	
}
