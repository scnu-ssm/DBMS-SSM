package com.chenrong.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chenrong.bean.DataBaseProperty;
import com.scnu.dao.DataBaseMapper;
import com.scnu.util.ConnectManager;

@Service
public class DataBaseService {
	
	@Autowired
	ConnectManager connectManager;
	
	/**
	 *  创建数据库
	 * @param connectId 连接的ID
	 * @param databaseName 数据库名字
	 * @param code 数据库编码
	 * @param sort 数据库索引格式
	 * @return
	 * @throws Exception
	 */
	public boolean createDateBase(String connectId, String databaseName, String code, String sort) throws Exception {
		
		SqlSession sqlsession = connectManager.getSessionAutoCommitByConnectId(connectId);
		Connection connect = sqlsession.getConnection();
		Statement statement = connect.createStatement();
		
		StringBuilder sb = new StringBuilder("create database " + databaseName);
		// 字符集不为空
		if(code != null && code.length() != 0) {
			sb.append(" default charset " + code);
		}
		// 排序规则不为空
		if(sort != null && sort.length() != 0) {
			sb.append(" collate " + sort);
		}
		// 拼装好的SQL语句
		String SQL = sb.toString();
		System.out.println("SQL = " + SQL);
		boolean taf = false;
		try {
		   statement.execute(SQL);
		   taf = true;
		}catch(Exception e) {
		   e.printStackTrace();
		}finally {
		   statement.close();
		   connect.close();
		   sqlsession.close();
		}
		
		return taf;
		
	}
	
	// 删除数据库
	public boolean deleteDateBase(String connectId, String databaseName) throws Exception {
		
		SqlSession sqlsession = connectManager.getSessionAutoCommitByConnectId(connectId);
		Connection connect = sqlsession.getConnection();
		Statement statement = connect.createStatement();
		
		boolean taf = false;
		try {
		   statement.execute("drop database " + databaseName);
		   taf = true;
		}catch(Exception e) {
		   e.printStackTrace();
		}finally {
		   statement.close();
		   connect.close();
		   sqlsession.close();
		}
		
		return taf;
	}
	
	// 查询数据库
	public ArrayList<String> showDateBase(String connectId) throws Exception{
		ArrayList<String> list = new ArrayList<String>();
		SqlSession sqlsession = connectManager.getSessionAutoCommitByConnectId(connectId);
		Connection connect = sqlsession.getConnection();
		Statement statement = connect.createStatement();
		try {
		   ResultSet result = statement.executeQuery("show databases");
		   while(result.next()) {
			   list.add(result.getString(1));
		   }
		} finally {
		   statement.close();
		   connect.close();
		   sqlsession.close();
		}
		// 按照数据库名字排序
		Collections.sort(list);
		return list;
		
	}
	
	// 查询数据库的字符集和排序规则
	public  DataBaseProperty showProperty(String connectId, String databaseName) throws Exception {
		
		SqlSession sqlsession = connectManager.getSessionAutoCommitByConnectId(connectId);
		Connection connect = sqlsession.getConnection();
		Statement statement = connect.createStatement();
		
		statement.executeQuery("use " + databaseName);
		DataBaseProperty  dataBaseProperty = new DataBaseProperty();
		dataBaseProperty.setDatabaseName(databaseName);
		ResultSet resultSet = statement.executeQuery("show variables like 'character_set_database'");
		while(resultSet.next()) {
			dataBaseProperty.setCharacterSetDatabase(resultSet.getString("Value"));
		}
		resultSet = statement.executeQuery("show variables like 'collation_database' ");
		while(resultSet.next()) {
			dataBaseProperty.setCollationDatabase(resultSet.getString("Value"));
		}
		statement.close();
		connect.close();
		sqlsession.close();
		
		return dataBaseProperty;
	}
	
	// 更新数据的字符集和排序规则
    public boolean updateDataBase(String connectId, String databaseName, String characterSetDatabase, String collationDatabase) throws Exception{
    	
		SqlSession sqlsession = connectManager.getSessionAutoCommitByConnectId(connectId);
		Connection connect = sqlsession.getConnection();
		Statement statement = connect.createStatement();
		boolean taf = true;
		
		try {
		if(characterSetDatabase != null) {
			String SQL = "alter database " + databaseName + " default character set " + characterSetDatabase;
			System.out.println(" SQL = " + SQL);
		    statement.execute(SQL);
		}
		
		if(collationDatabase != null) {
			String SQL = "alter database " + databaseName + " default collate " + collationDatabase;
			System.out.println("SQL = " + SQL);
		    statement.execute(SQL);
		}
		}catch(Exception e){
			taf = false;
			e.printStackTrace();
		}
		statement.close();
		connect.close();
		sqlsession.close();
    	return taf;
    	
    }
    
    /**
     * 获取数据库的字符集
     * @param connectId 连接Id
     * @return
     * @throws Exception
     */
    public List<String> getCharacterSets(String connectId) throws Exception {
    	SqlSession sqlsession = connectManager.getSessionAutoCommitByConnectId(connectId);
    	try {
    	     DataBaseMapper dataBaseMapper = sqlsession.getMapper(DataBaseMapper.class);
    	     List<String> characterSets = dataBaseMapper.selectCharsets();
    	     return characterSets;
    	} finally {
    		sqlsession.close();
    	}
    }
    
    /**
     * 根据字符集，获取数据库的排序集
     * @param connectId
     * @param characterSet
     * @return
     */
    public List<String> getCollations(String connectId, String characterSet) throws Exception{
    	SqlSession sqlsession = connectManager.getSessionAutoCommitByConnectId(connectId);
    	try {
    		DataBaseMapper dataBaseMapper = sqlsession.getMapper(DataBaseMapper.class);
    		List<String> collations = dataBaseMapper.selectCollations(characterSet);
    		return collations;
    	} finally {
    		sqlsession.close();
    	}
    }
    
    /**
     *    查询数据库的版本
     * @param connectId 连接Id
     * @return
     * @throws Exception
     */
    public String getVersion(String connectId) throws Exception{
    	SqlSession sqlsession = connectManager.getSessionAutoCommitByConnectId(connectId);
    	try {
    		DataBaseMapper dataBaseMapper = sqlsession.getMapper(DataBaseMapper.class);
    		String version = dataBaseMapper.getVersion();
    		return version;
    	} finally {
    		sqlsession.close();
    	}
    }
    
}
