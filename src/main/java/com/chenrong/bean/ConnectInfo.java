package com.chenrong.bean;

/**
 *  数据库连接的信息
 * @author chenrong
 */
public class ConnectInfo {
	
    private String connectId;
    
    private String userId; 

	private String connectName;
	
	private String host;
	
	private int port;
	
	private String username;
	
	private String password;
	
	private int isSave;
	
	public int getIsSave() {
		return isSave;
	}

	public void setIsSave(int isSave) {
		this.isSave = isSave;
	}

	public String getConnectId() {
		return connectId;
	}

	public void setConnectId(String connectId) {
		this.connectId = connectId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getConnectName() {
		return connectName;
	}

	public void setConnectName(String connectName) {
		this.connectName = connectName;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "ConnectInfo [connectId=" + connectId + ", userId=" + userId + ", connectName=" + connectName + ", host="
				+ host + ", port=" + port + ", username=" + username + ", password=" + password + ", isSave=" + isSave
				+ "]";
	}
	
}
