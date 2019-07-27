package com.zichang.bean;

/**
 * 表的索引
 * @author 麻瓜
 *索引【名，字段，索引类型，索引方法】
 *  FULLTEXT INDEX `tt`(`id`) USING HASH,
 *  索引类型：FULLTEXT/NORMAL/SPATIAL/UNIQUE
 *  索引方法：HASH/BTREE
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
