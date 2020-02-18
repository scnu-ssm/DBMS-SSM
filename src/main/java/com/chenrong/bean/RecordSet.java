package com.chenrong.bean;

import java.util.List;
import java.util.Map;

/**
 * װ�����ݱ�ļ�¼���ķ�������
 * @author chenrong
 *
 */
public class RecordSet {
	
	private Page page;  // ҳ��Ϣ
	
	private List<String> primaryKeys;  // ��������
	
	private List<String> columnsName;  // ������
	
	private Map<String, String> types;  // ���ֶ����� 
	
	private List<Map<String, Object>> records;  // ��¼��

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
