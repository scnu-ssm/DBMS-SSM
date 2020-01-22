package com.chenrong.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chenrong.bean.ConnectInfo;
import com.chenrong.bean.DataBaseProperty;

@Service
public class DataBaseService {
	
	@Autowired
	ConnectInfoService connectInfoService;
	
	// 创建数据库
	public boolean createDateBase(String connectId, String databaseName, String code, String sort) throws Exception {
		
		ConnectInfo connect = connectInfoService.selectByConnectId(connectId);
		Connection con = ConnectMySQL.getConnect(connect);
		Statement statement = con.createStatement();
		boolean taf = false;
		try {
		statement.execute("create database " + databaseName + " default charset " + code + " collate " + sort);
		taf = true;
		}catch(Exception e) {
		e.printStackTrace();
		System.out.println("message = " + e.getMessage());
		con.close();
		}
		return taf;
	}
	
	// 删除数据库
	public boolean deleteDateBase(String connectId, String databaseName) throws Exception {
		
		ConnectInfo connect = connectInfoService.selectByConnectId(connectId);
		Connection con = ConnectMySQL.getConnect(connect);
		Statement statement = con.createStatement();
		
		boolean taf = false;
		try {
		statement.execute("drop database " + databaseName);
		taf = true;
		}catch(Exception e) {
		e.printStackTrace();
		con.close();
		}
		
		return taf;
	}
	
	// 查询数据库
	public ArrayList<String> showDateBase(String connectId) throws Exception{
		ArrayList<String> list = new ArrayList<String>();
		ConnectInfo connect = connectInfoService.selectByConnectId(connectId);
		Connection con = ConnectMySQL.getConnect(connect);
		Statement statement = con.createStatement();
		ResultSet result = statement.executeQuery("show databases");
		while(result.next())
			list.add(result.getString("Database"));
		con.close();
		
		return list;
		
	}
	
	// 查询数据库的字符集和排序规则
	public  DataBaseProperty showProperty(String connectId, String databaseName) throws Exception {
		
		ConnectInfo connect = connectInfoService.selectByConnectId(connectId);
		Connection con = ConnectMySQL.getConnect(connect);
		Statement statement = con.createStatement();
		statement.executeQuery("use " + databaseName);
		DataBaseProperty  dataBaseProperty = new DataBaseProperty();
		ResultSet resultSet = statement.executeQuery("show variables like 'character_set_database' ");
		while(resultSet.next()) {
			dataBaseProperty.setCharacter_set_database(resultSet.getString("Value"));
		}
		resultSet = statement.executeQuery("show variables like 'collation_database' ");
		while(resultSet.next()) {
			dataBaseProperty.setCollation_database(resultSet.getString("Value"));
		}
		
		con.close();
		return dataBaseProperty;
	}
	
	// 更新数据的字符集和排序规则
    public boolean updateDataBase(String connectId, String databaseName, String character_set_database, String collation_database) throws Exception{
    	
    	ConnectInfo connect = connectInfoService.selectByConnectId(connectId);
		Connection con = ConnectMySQL.getConnect(connect);
		Statement statement = con.createStatement();
		boolean taf = true;
		
		try {
		if(character_set_database != null) {
			String SQL = "alter database " + databaseName + " default character set " + character_set_database;
			System.out.println(" SQL = " + SQL);
		statement.execute(SQL);
		}
		
		if(collation_database != null) {
			String SQL = "alter database " + databaseName + " default collate " + collation_database;
			System.out.println("SQL = " + SQL);
		statement.execute(SQL);
		}
		}catch(Exception e){
			taf = false;
			e.printStackTrace();
		}
    	
		con.close();
    	return taf;
    }
}
