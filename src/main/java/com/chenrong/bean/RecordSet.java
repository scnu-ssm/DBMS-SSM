package com.chenrong.bean;

import java.util.List;
import java.util.Map;

/**
 * 装载数据表的记录集的返回数据
 * @author chenrong
 *
 */
public class RecordSet {
	
	private Page page;  // 页信息
	
	private List<String> primaryKeys;  // 主键集合
	
	private List<String> columnsName;  // 列名字
	
	private Map<String, String> types;  // 列字段类型 
	
	private List<Map<String, Object>> records;  // 记录集

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

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

	public Map<String, String> getTypes() {
		return types;
	}

	public void setTypes(Map<String, String> types) {
		this.types = types;
	}

	@Override
	public String toString() {
		return "RecordSet [page=" + page + ", primaryKeys=" + primaryKeys + ", columnsName=" + columnsName + ", types="
				+ types + ", records=" + records + "]";
	}
	
}
