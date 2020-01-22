package com.chenrong.service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.chenrong.bean.ConnectInfo;
import com.mchange.v2.c3p0.ComboPooledDataSource;

// 获取数据源
public class ConnectMySQL {
	
	static Map<String, ComboPooledDataSource> poolMap = new HashMap<String, ComboPooledDataSource>();
	
	@Autowired
	ConnectInfoService connectInfoService;
	
	public static Connection getConnect(ConnectInfo connect) throws Exception {
		
		String id = connect.getConnectId();
		if(!poolMap.containsKey(id)) {
			ComboPooledDataSource dataSource = new ComboPooledDataSource();
			dataSource.setDriverClass("com.mysql.jdbc.Driver");
			String username = connect.getUsername();
			String password = connect.getPassword();
			String host = connect.getHost();
			Integer port = connect.getPort();
			dataSource.setJdbcUrl("jdbc:mysql://" + host + ":" + port);
			dataSource.setUser(username);
			dataSource.setPassword(password);
			
			poolMap.put(id, dataSource);
		}
		
		System.out.println(" map size = " + poolMap.size());
		ComboPooledDataSource dataSource = poolMap.get(id);
		
		return dataSource.getConnection();
		
	}
	
    public static void closeConnect(ConnectInfo connect) {
		String id = connect.getConnectId();
		
		poolMap.remove(id);
		
		System.out.println(" map size = " + poolMap.size());
	}

}
