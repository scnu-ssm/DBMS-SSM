package com.chenrong.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chenrong.bean.ConnectInfo;

@Service
public class BackupService {
	
	@Autowired
	ConnectInfoService connectInfoService;
	
	public boolean BackUp(String username, String password, String database, String form, String dest) {
		
		String base = "mysqldump  --no-defaults -u" + username + " -p" + password + " " + database;
		if(form != null)
			base = base + " " + form;
		String path  = " > " + dest;
		
		String cmd = base + path;
		System.out.println(" cmd = " + cmd);
		ProcessBuilder pb = new ProcessBuilder("cmd", "/c", cmd);
		boolean taf = false;
		
		try {
			Process p = pb.start();
			p.waitFor();
			taf = true;
			System.out.println("转存sql语句成功");
		} catch (Exception e) {
			taf = false;
			e.printStackTrace();
		}
		
		return taf;
	}
	
	public boolean Recovery(String connectId, String database, String src) throws Exception {
		
		ConnectInfo connect = connectInfoService.selectByConnectId(connectId);
		System.out.println("开始恢复...");
		Connection con = ConnectMySQL.getConnect(connect);
		Statement statement = con.createStatement();
		statement.executeQuery("use " + database);
		
		boolean taf = true;
		try {
		ScriptRunner runner = new ScriptRunner(con);
        runner.setLogWriter(null);//设置是否输出日志
        // 绝对路径读取
        Reader read = new BufferedReader(new InputStreamReader(new FileInputStream(src), "UTF-8"));
        runner.runScript(read);
        runner.closeConnection();
		}catch(Exception e) {
			taf = false;
			e.printStackTrace();
		}
        con.close();
        
        System.out.println("恢复完成");
	 	return taf;
	}
	
    public boolean closeConnect(ConnectInfo connect){
		
    	try {
    	ConnectMySQL.closeConnect(connect);
    	
    	}catch(Exception e) {
    		e.printStackTrace();
    		return false;
    	}
		
	 	return true;
	}

}
