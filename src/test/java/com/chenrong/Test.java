package com.chenrong;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;



public class Test {
	
    public final static String RootPath = System.getProperty("user.dir");
    
    public final static String DataBaseName = "DBMS";
    
    //public final static String JDBCURL = "jdbc:derby:" + RootPath + File.separator + DataBaseName + "; create = true";
    public final static String JDBCURL = "jdbc:derby:D:/Users/DBMS-SSM/DBMS; create = true";
    
    public final static String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";

	public static void main(String[] args) throws Exception{
	         Class.forName(DRIVER).newInstance();
	         Connection con = DriverManager.getConnection(JDBCURL);
	         // 获取命令执行体
	         Statement statement = con.createStatement();
//	         statement.execute("DROP TABLE SCNU_User");
//	         System.out.println("删除 SCNU_User 成功");
//	         statement.execute("DROP TABLE SCNU_ConnectInfo");
//	         System.out.println("删除 SCNU_ConnectInfo 成功");
	      // 获取数据库的信息
			 DatabaseMetaData meta = con.getMetaData();
		  // 得到数据表的集合
			 ResultSet result = meta.getTables(null, null, null, new String[] {"TABLE"});
			 Set<String> set = new HashSet();
			 while(result.next()) {
				set.add(result.getString("TABLE_NAME"));
			 }
			 for(String name : set) {
				 System.out.println(name);
			 }
			 ResultSet resultSet = statement.executeQuery("select * from SCNU_USER");
			 System.out.println("SCNU_USER表");
			 while(resultSet.next()) {
				 System.out.println(resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3) + resultSet.getString(4));
			 }
			 System.out.println("=======================");
			 System.out.println("SCNU_CONNECTINFO表");
			 ResultSet resultSet2 = statement.executeQuery("select * from SCNU_CONNECTINFO");
             while(resultSet2.next()) {
            	 System.out.println(resultSet2.getString(1) + " " + resultSet2.getString(2) + " " + resultSet2.getString(3) + resultSet2.getString(4));
			 }
             
	         // 关闭连接
	         con.close();
	}

}
