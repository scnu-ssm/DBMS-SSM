package com.chenrong.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chenrong.bean.Demo;
import com.scnu.dao.DemoMapper;
import com.scnu.util.ConnectManager;

@Service
/**
 * 通过connectId，获取sqlSession，再获取mapper接口，再执行我们的方法
 * @author chenrong
 *
 */
public class DemoService {
       
	@Autowired
	ConnectManager connectManager;
	
	// 增加Demo用户
    public int insert(Demo demo, String connectId, String database) throws Exception{
    	SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(connectId);
    	try {
    		 // 注意，获取Mapper，需要通过这个方式获取
    	     DemoMapper demoMapper = sqlSession.getMapper(DemoMapper.class);
    	     return demoMapper.insert(database, demo.getId(), demo.getUsername(), demo.getPassword());
    	} finally {
    		 sqlSession.close();
    	}
    }
    
    // 查找Demo用户
    public Demo selectById(String id, String connectId, String database) throws Exception{
    	SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(connectId);
    	try {
    	      DemoMapper demoMapper = sqlSession.getMapper(DemoMapper.class);
    	      return demoMapper.selectById(database, id);
    	} finally {
    		  sqlSession.close();
    	}
    }
}
