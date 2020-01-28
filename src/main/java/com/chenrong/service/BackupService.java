package com.chenrong.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chenrong.bean.ConnectInfo;
import com.chenrong.bean.ConnectVO;
import com.scnu.util.ConnectManager;

@Service
public class BackupService {
	
	@Autowired
	ConnectManager connectManager;
	
	@Autowired
	ConnectInfoService connectInfoService;
	
	// 将数据库备份到SQL语句
	public boolean BackUp(String connectId, String database, String dest) {
		
		ConnectInfo connectInfo = connectInfoService.selectByConnectId(connectId);
		String username = connectInfo.getUsername();
		String password = connectInfo.getPassword();
		String host = connectInfo.getHost();
		Integer port = connectInfo.getPort();
		String base = "mysqldump  --no-defaults -h " + host + " -P " + port +  " -u" + username + " -p" + password + " " + database;
		String path  = " > " + dest;
		
		String cmd = base + path;
		System.out.println("cmd = " + cmd);
		ProcessBuilder pb = new ProcessBuilder("cmd", "/c", cmd);
		boolean taf = false;
		try {
			Process p = pb.start();
			p.waitFor();
			taf = true;
			System.out.println("转存sql语句成功");
		} catch (Exception e) {
			e.printStackTrace();
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
		
		try {
		ScriptRunner runner = new ScriptRunner(connect);
        runner.setLogWriter(null);//设置是否输出日志
        // 绝对路径读取
        Reader read = new BufferedReader(new InputStreamReader(new FileInputStream(src), "UTF-8"));
        runner.runScript(read);
        runner.closeConnection();
		}catch(Exception e) {
			e.printStackTrace();
			// 出现异常
			return 0;
		}
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

}
