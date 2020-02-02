package com.chenrong.bean;

import java.util.List;
import java.util.Map;

/**
 * װ�����ݱ��ļ�¼���ķ�������
 * @author chenrong
 *
 */
public class RecordSet {
	
	private List<String> primaryKeys;  // ��������
	
	private List<String> columnsName;  // ������
	
	private List<Map<String, Object>> records;  // ��¼��

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