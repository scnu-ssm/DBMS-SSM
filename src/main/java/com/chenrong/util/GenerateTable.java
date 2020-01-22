package com.chenrong.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.chenrong.bean.Const;

/**
 *  用于自动生成数据表User、ConnectInfo
 * @author chenrong
 */
public class GenerateTable {
	
     public final static String GENERATE_USER_TABLE = "create table " + Const.USER_TABLE_NAME +
    		                 "(id varchar(32) NOT NULL, " + 
    		                 "username varchar(20) NOT NULL, " + 
    		                 "password varchar(20) NOT NULL, " + 
    		                 "email varchar(25) NOT NULL, " + 
    		                 "PRIMARY KEY(id))";
     
     public final static String GENERATE_CONNECTINFO_TABLE = "create table " + Const.CONNECTINFO_TABLE_NAME + 
    		                 "(connect_Id varchar(32) NOT NULL, " +
    		                 "user_Id varchar(32) NOT NULL, " + 
    				         "connect_Name varchar(25) NOT NULL, " + 
    				         "host varchar(30) NOT NULL, " + 
    				         "port int NOT NULL, " + 
    				         "username varchar(20) NOT NULL, " + 
    				         "password varchar(20) NOT NULL, " +
    				         "isSave int NOT NULL, " + 
    				         "PRIMARY KEY (connect_Id))";
     
     public final static String RootPath = System.getProperty("user.dir");
     
     public final static String DataBaseName = "DBMS";
     
     public final static String JDBCURL = "jdbc:derby:" + RootPath + File.separator + DataBaseName + "; create = true";
     //public final static String JDBCURL = "jdbc:derby:D:/Users/DBMS-SSM/DBMS; create = true";
     
     public final static String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
     
     static {
 		try {
 			// 加载驱动
			Class.forName(DRIVER).newInstance();
			// 得到连接
			Connection con = DriverManager.getConnection(JDBCURL);
			// 获取数据库的信息
			DatabaseMetaData meta = con.getMetaData();
			// 得到数据表的集合
			ResultSet result = meta.getTables(null, null, null, new String[] {"TABLE"});
			Set<String> set = new HashSet();
			while(result.next()) {
				set.add(result.getString("TABLE_NAME").toLowerCase());
			}
			Statement statement = con.createStatement();
			
			// 把原来表的全删了
//			for(String name : set) {
//			    statement.execute("DROP TABLE " + name);
//			    System.out.println("删除 " + name);
//			}
			
			// 创建User表
			if(!set.contains(Const.USER_TABLE_NAME.toLowerCase())) {
				statement.execute(GENERATE_USER_TABLE);
				System.out.println("derby: 创建" + Const.USER_TABLE_NAME + "表成功");
			}
			
			// 创建ConnectInfo表
			if(!set.contains(Const.CONNECTINFO_TABLE_NAME.toLowerCase())) {
				statement.execute(GENERATE_CONNECTINFO_TABLE);
				System.out.println("derby: 创建" + Const.CONNECTINFO_TABLE_NAME + "表成功");
			}
			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
     }
     
}
