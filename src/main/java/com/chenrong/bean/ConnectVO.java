package com.chenrong.bean;

// �������ݿ�ʱ������ʹ�õ�����Ϣ����װ��VO
public class ConnectVO {
	
	private String connectId;
	
	private String database;
	
	private String table;

	public String getConnectId() {
		return connectId;
	}

	public void setConnectId(String connectId) {
		this.connectId = connectId;
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

	@Override
	public String toString() {
		return "ConnectVO [connectId=" + connectId + ", database=" + database + ", table=" + table + "]";
	}

}
