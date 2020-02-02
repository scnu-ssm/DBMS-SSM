package com.chenrong.bean;

import java.util.List;
import java.util.Map;

/**
 * 装载数据表的记录集的返回数据
 * @author chenrong
 *
 */
public class RecordSet {
	
	private List<String> primaryKeys;  // 主键集合
	
	private List<String> columnsName;  // 列名字
	
	private List<Map<String, Object>> records;  // 记录集

	public List<String> getPrimaryKeys() {
		return primaryKeys;
	}

	public void setPrimaryKeys(List<String> primaryKeys) {
		this.primaryKeys = primaryKeys;
	}

	public List<String> getColumnsName() {
		return columnsName;
	}

	public void setColumnsName(List<String> columnsName) {
		this.columnsName = columnsName;
	}

	public List<Map<String, Object>> getRecords() {
		return records;
	}

	public void setRecords(List<Map<String, Object>> records) {
		this.records = records;
	}

	@Override
	public String toString() {
		return "RecordSet [primaryKeys=" + primaryKeys + ", columnsName=" + columnsName + ", records=" + records + "]";
	}

}
