package com.zichang.bean;

import java.util.List;

public class Privilege {
	private String connectId;
	private String username;
	private String host;
	private String database;
	private String table;
	private String[] privs;
	public String[] getPrivs() {
		return privs;
	}
	public void setPrivs(String[] privs) {
		this.privs = privs;
	}
	public String getConnectId() {
		return connectId;
	}
	public void setConnectId(String connectId) {
		this.connectId = connectId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	
}
