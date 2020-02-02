package com.chenrong.bean;

import java.util.List;
import java.util.Map;

/**
 * 操作数据表记录的逻辑VO对象
 * @author chenrong
 *
 */
public class TableVO {
	
	private String connectId;  // 连接的Id
	
	private String database;  // 数据库的名称
	
	private String table;  // 数据表的名称
	
	private List<String> primaryKeys; // 数据表的主键，唯一确定一条记录
	
	private Map<String, Object> newRecord;  // 单条新记录的内容， key代表字段名称， value对应字段的值
	
	private Map<String, Object> oldRecord;  // 单条旧记录的内容， key代表字段名称， value对应字段的值
	
	private Integer current; // 当前的页码
	
	private String orderColumn;  // 排序的字段
	
	private String orderType;  // 排序的方式

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
