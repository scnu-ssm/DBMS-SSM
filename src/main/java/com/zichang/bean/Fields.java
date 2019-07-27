package com.zichang.bean;

import java.util.List;

/**
 * 表的字段
 * @author zichang
 * 字段【名，类型，长度，小数点，是否null，是否主键】
 */
public class Fields {
	private String name;
	private String type;
	private String length;
	private String point;
	private int notNull;
	private int primary;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}

	public int getNotNull() {
		return notNull;
	}
	public void setNotNull(int notNull) {
		this.notNull = notNull;
	}
	public int getPrimary() {
		return primary;
	}
	public void setPrimary(int primary) {
		this.primary = primary;
	}
	@Override
	public String toString() {
		return "Fields [name=" + name + ", type=" + type + ", length=" + length + ", point=" + point + ", notNull="
				+ notNull + ", primary=" + primary + "]";
	}
	 
}
