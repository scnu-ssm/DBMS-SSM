package com.zichang.bean;

/**
 * �������
 * @author ���
 *�����������ֶΣ��������ͣ�����������
 *  FULLTEXT INDEX `tt`(`id`) USING HASH,
 *  �������ͣ�FULLTEXT/NORMAL/SPATIAL/UNIQUE
 *  ����������HASH/BTREE
 * 
 */
public class Indexes {
	private String name;
	private String field;
	private String type;
	private String method;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	@Override
	public String toString() {
		return "Indexes [name=" + name + ", field=" + field + ", type=" + type + ", method=" + method + "]";
	}
	
}
