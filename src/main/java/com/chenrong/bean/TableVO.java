package com.chenrong.bean;

import java.util.List;
import java.util.Map;

/**
 * �������ݱ��¼���߼�VO����
 * @author chenrong
 *
 */
public class TableVO {
	
	private String connectId;  // ���ӵ�Id
	
	private String database;  // ���ݿ������
	
	private String table;  // ���ݱ������
	
	private List<String> primaryKeys; // ���ݱ��������Ψһȷ��һ����¼
	
	private Map<String, Object> newRecord;  // �����¼�¼�����ݣ� key�����ֶ����ƣ� value��Ӧ�ֶε�ֵ
	
	private Map<String, Object> oldRecord;  // �����ɼ�¼�����ݣ� key�����ֶ����ƣ� value��Ӧ�ֶε�ֵ
	
	private Integer current; // ��ǰ��ҳ��
	
	private String orderColumn;  // ������ֶ�
	
	private String orderType;  // ����ķ�ʽ

	public Integer getCurrent() {
		return current;
	}

	public void setCurrent(Integer current) {
		this.current = current;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

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

	public Map<String, Object> getNewRecord() {
		return newRecord;
	}

	public void setNewRecord(Map<String, Object> newRecord) {
		this.newRecord = newRecord;
	}

	public Map<String, Object> getOldRecord() {
		return oldRecord;
	}

	public void setOldRecord(Map<String, Object> oldRecord) {
		this.oldRecord = oldRecord;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public List<String> getPrimaryKeys() {
		return primaryKeys;
	}

	public void setPrimaryKeys(List<String> primaryKeys) {
		this.primaryKeys = primaryKeys;
	}

	@Override
	public String toString() {
		return "TableVO [connectId=" + connectId + ", database=" + database + ", table=" + table + ", primaryKeys="
				+ primaryKeys + ", newRecord=" + newRecord + ", oldRecord=" + oldRecord + ", current=" + current
				+ ", orderColumn=" + orderColumn + ", orderType=" + orderType + "]";
	}
	
}
