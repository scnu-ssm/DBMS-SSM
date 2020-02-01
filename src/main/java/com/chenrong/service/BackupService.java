package com.chenrong.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chenrong.bean.ConnectInfo;
import com.chenrong.bean.ConnectVO;
import com.chenrong.util.GenerateIDUtil;
import com.scnu.util.ConnectManager;

@Service
public class BackupService {
	
	public final static String RootPath = System.getProperty("user.dir");
	
	public final static String TempPath = RootPath + File.separator + "SQL";
	
	public final static String suffix = ".sql";
	
	@Autowired
	ConnectManager connectManager;
	
	@Autowired
	ConnectInfoService connectInfoService;
	
	// 将数据库的数据表备份到SQL语句,调用MySQL客户端,若客户没有客户端无法使用，该接口存在缺陷
	@Deprecated
	public boolean BackUpTwo(String connectId, String database, List<String> tables, String dest) {
		
		ConnectInfo connectInfo = connectInfoService.selectByConnectId(connectId);
		String username = connectInfo.getUsername();
		String password = connectInfo.getPassword();
		String host = connectInfo.getHost();
		Integer port = connectInfo.getPort();
		String base = "mysqldump  --no-defaults -h " + host + " -P " + port +  " -u" + username + " -p" + password + " " + database + " ";
		StringBuilder sb = new StringBuilder(base);
	    sb.append("--tables ");
		for(String table : tables) {
			  sb.append(table + " ");
		}
		sb.append(" > ").append(dest);
		String cmd  = sb.toString();
		System.out.println("cmd = " + cmd);
		ProcessBuilder pb = new ProcessBuilder("cmd", "/c", cmd);
		boolean taf = false;
		Process process = null;
		try {
			process = pb.start();
			process.waitFor();
			taf = true;
			System.out.println("转存sql语句成功");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 完成任务销毁
			if(process != null) {
				process.destroy();
			}
		}
		
		return taf;
	}
	
	// 还原SQL文件到指定数据库
	public Integer Recovery(String connectId, String database, String src) throws Exception {
		
		// 不存在连接
		if(connectInfoService.selectByConnectId(connectId) == null) {
			     return -1;
		}
		
		System.out.println("开始恢复...");
		SqlSession sqlsession = connectManager.getSessionAutoCommitByConnectId(connectId);
		Connection connect = sqlsession.getConnection();
		Statement statement = connect.createStatement();
		statement.executeQuery("use " + database);
		
		ScriptRunner runner = new ScriptRunner(connect);
        runner.setLogWriter(null);//设置是否输出日志
        // 绝对路径读取
        Reader read = new BufferedReader(new InputStreamReader(new FileInputStream(src), "UTF-8"));
        runner.runScript(read);
        runner.closeConnection();
		statement.close();
		connect.close();
		sqlsession.close();
        
        System.out.println("恢复完成");
        // 操作成功
	 	return 1;
	}
	
    // 将数据表的内容导出到csv文件中
    public void leadingOutCSV(ConnectVO connectVO, String path) throws Exception{
    	
		SqlSession sqlsession = connectManager.getSessionAutoCommitByConnectId(connectVO.getConnectId());
		Connection connect = sqlsession.getConnection();
		Statement statement = connect.createStatement();
		ResultSet res = statement.executeQuery("select * from " + connectVO.getDatabase() + "." + connectVO.getTable());

		// 导出数据表的数据到csv
		CSVPrinter printer = null;
		try {
            FileOutputStream fos = new FileOutputStream(path);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "GBK");
            printer = CSVFormat.EXCEL.print(osw);
            printer.printRecords(res);
            printer.flush();
		}finally {
			if(printer != null) {
	           printer.close();
			}
		}
 
    }
    
    // 将数据库指定的数据表导出到SQL文件中,返回生成的SQL文件路径
    public String BackupTable(String connectId, String database, String table, boolean isOnlyStructor) throws Exception{
    	   File file = new File(TempPath);
    	   // 如果不存在该路径就创建该文件
    	   if(!file.exists()) {
    		    file.mkdirs();
    	   }
    	   String fileName = GenerateIDUtil.getUUID32() + suffix;
    	   // SQL文件的输出路径
    	   String path = TempPath + File.separator + fileName;
    	   List<String> tables = new ArrayList();
    	   tables.add(table);
    	   Backup(connectId, database, tables, path, isOnlyStructor);
    	   return path;
    }
    
    /**
     * 将数据表列表备份到指定SQL文件，自己解析数据库内容生成SQL语句再写入SQL文件
     * @param connectId 连接Id
     * @param database 数据库名称
     * @param tables 数据表名称列表
     * @param dest 输出文件路径
     * @param isOnlyStructor 是否仅备份数据表结构
     * @throws Exception
     */
    public void Backup(String connectId, String database, List<String> tables, String dest, boolean isOnlyStructor) throws Exception{
    	   SqlSession sqlSession = connectManager.getSessionAutoCommitByConnectId(connectId);
    	   // 获取文件
    	   File file = generateFile(dest);
    	   FileOutputStream fos = new FileOutputStream(file);
    	   OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
    	   BufferedWriter bw = new BufferedWriter(osw);
    	   // SQL文件的logo标题
    	   bw.write("-- DBMS-SCNU dump ;  Time: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n\n");
    	   Connection con = sqlSession.getConnection();
    	   Statement statement = con.createStatement();
    	   
    	   // 逐个表进行备份
    	   for(String table : tables) {
    		   // 创建表
    		   ResultSet resultSet = statement.executeQuery("show create table " + database + "." + table);
    		   bw.write("DROP TABLE IF EXISTS `" + table + "`;" + "\n");
    		   while(resultSet.next()) {
        		   String tableStructor = resultSet.getString(2) + ";";
        		   bw.write(tableStructor.replace("\n", " ") + "\n\n");
    		   }
    		   
    		   // 插入表的内容
    		   if(!isOnlyStructor) {
    			   // 加锁
    			   bw.write("LOCK TABLES `" + table + "` WRITE;" + " \n");
    			   
    			   resultSet = statement.executeQuery("select  * from " + database + "." + table);
    	           ResultSetMetaData metaData = resultSet.getMetaData();
    	           Integer count =  metaData.getColumnCount();
    	           StringBuilder sb = new StringBuilder();
    	           resultSet.last();
    	           int rowCount = resultSet.getRow();
    	           resultSet.beforeFirst();
    	           // 防止查询内容为NULL
    	           if(rowCount != 0) {
    	                 sb.append("INSERT INTO `" + table + "` VALUES ");
    	           }
    	           while (resultSet.next()){
    	                 sb.append("(");
    	                 for(int i = 1; i < count; i++){
    	                	 String temp = resultSet.getString(i);
    	                     // 防止内容为NULL以及处理换行符
    	                	 if(temp == null) {
    	                		 sb.append(temp).append(",");
    	                	 }else {
    	                		 if(temp.contains("\r\n")) {
    	                			 temp = temp.replace("\r\n", "\\r\\n");
    	                		 }else {
    	                			 temp = temp.replace("\n", "\\n");
    	                		 }
    	                		 sb.append("'").append(temp).append("',");
    	                	 }
    	                 }
    	                 String temp = resultSet.getString(count);
	                	 if(temp == null) {
	                		 sb.append(temp);
	                	 }else {
	                		 if(temp.contains("\r\n")) {
	                			temp = temp.replace("\r\n", "\\r\\n");
	                		 }else {
	                			temp = temp.replace("\n", "\\n");
	                		 }
    	                     sb.append("'").append(temp).append("'");
	                	 }
    	                 sb.append("),");
    	           }
    	           if(sb.length() != 0) {
    	                 String str = sb.substring(0, sb.length() - 1) + ";";
    	                 bw.write(str + "\n");
    	           }
    	           
    	           // 解锁
    	           bw.write("UNLOCK TABLES;\n\n");
    		   }
    		   bw.flush();
    	   }
    	   
    	   bw.close();
    	   statement.close();
    	   con.close();
    }
    
    /**
     * 创建文件
     * @param dest 文件的路径，包含文件的名称
     * @return 返回文件对象
     */
    public static File generateFile(String dest) throws Exception{
    	   File file = new File(dest);
    	   if(!file.exists()) {
    		   File parentFile = file.getParentFile();
    		   parentFile.mkdirs();
    		   file.createNewFile();
    	   }
    	   return file;
    }

}
