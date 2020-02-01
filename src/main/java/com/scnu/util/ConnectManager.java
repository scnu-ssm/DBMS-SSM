package com.scnu.util;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 获取连接的核心类，ConnectManager为单例的类，需要注意线程安全
 * @author chenrong
 */
@Component
public class ConnectManager {
	
	// Key的过期时间, 20分钟
	public final static Long expire = 20*60*1000L;
	//public final static Long expire = 60*1000L;
	
	@Autowired
	JDBCMyBatisUtil jDBCMyBatisUtil;
	
	private Map<String, SqlSessionFactory> pool = new ConcurrentHashMap();
	
	// 记录连接过期的时间，
	private Map<String, Long> expiresPool = new ConcurrentHashMap();
	
	// 通过connectId获取SqlSessionFactory
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
	
	// 通过connectId删除SqlSessionFactory
	public SqlSessionFactory deleteConnect(String connectId) {
		   SqlSessionFactory sqlSessionFactory = pool.remove(connectId);
		   expiresPool.remove(connectId);
		   return sqlSessionFactory;
	}
	
	// 获取能够执行SQL语句的SqlSession，该SqlSession默认自动提交事务
	public SqlSession getSessionAutoCommitByConnectId(String connectId) throws Exception {
		   SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(connectId);
		   if(sqlSessionFactory == null) {
			   throw new Exception("不存在 SqlSessionFactory !!!");
		   }
		   // 允许事务自动提交
		   return sqlSessionFactory.openSession(true);
	}
	
	// 获取能够执行SQL语句的SqlSession，该SqlSession需用要commit提交事务
	public SqlSession getSessionByConnectId(String connectId) throws Exception{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(connectId);
		   if(sqlSessionFactory == null) {
			   throw new Exception("不存在 SqlSessionFactory !!!");
		   }
		   // 允许事务自动提交
		   return sqlSessionFactory.openSession();
	}
	
	// 获取接口对应的Mapper, 参数为 connectId、 T.class, 默认自动提交事务
	public <T> T getMapper(String connectId, Class<T> type) throws Exception{
		   return getMapper(connectId, type, true);
	}
	
	// 获取接口对应的Mapper, 参数为 connectId、 T.class
	public <T> T getMapper(String connectId, Class<T> type, boolean isAutoCommit) throws Exception{
		   SqlSession sqlSession = null;
		   if(isAutoCommit) {
			   sqlSession = getSessionAutoCommitByConnectId(connectId);
		   }else {
			   sqlSession = getSessionByConnectId(connectId);
		   }
		   return sqlSession.getMapper(type);
	}
	
	// 得到expiresPool集合容器
	public Map geExpiresPoolMap() {
		   return expiresPool;
	}
	
}
