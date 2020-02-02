package com.chenrong.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chenrong.bean.Demo;
import com.scnu.dao.DemoMapper;
import com.scnu.util.ConnectManager;

@Service
/**
 * ͨ��connectId����ȡsqlSession���ٻ�ȡmapper�ӿڣ���ִ�����ǵķ���
 * @author chenrong
 *
 */
public class DemoService {
       
	@Autowired
	ConnectManager connectManager;
	
	// ����Demo�û�
    public int insert(Demo demo, String connectId, String database) throws Exception{
    	SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(connectId);
    	try {
    		 // ע�⣬��ȡMapper����Ҫͨ�������ʽ��ȡ
    	     DemoMapper demoMapper = sqlSession.getMapper(DemoMapper.class);
    	     return demoMapper.insert(database, demo.getId(), demo.getUsername(), demo.getPassword());
    	} finally {
    		 sqlSession.close();
    	}
    }
    
    // ����Demo�û�
    public List<Map> selectById(String id, String connectId, String database) throws Exception{
    	SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(connectId);
    	try {
    	      DemoMapper demoMapper = sqlSession.getMapper(DemoMapper.class);
    	      return demoMapper.selectById(database, id);
    	} finally {
    		  sqlSession.close();
    	}
    }
}
