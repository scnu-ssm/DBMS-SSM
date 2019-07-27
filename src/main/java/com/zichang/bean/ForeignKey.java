package com.zichang.bean;

/**
 * 表的外键
 * @author zichang
 *外键【名，字段，参考模式（数据库名），参考表（表名），参考字段，删除时，更新时】
 *CONSTRAINT `tt` FOREIGN KEY (`id`) REFERENCES `test`.`table2` (`na`) ON DELETE CASCADE ON UPDATE CASCADE
 *删除时，更新时CASCADE/NO ACTION/RESTRICT/SET NULL
 */
public class ForeignKey {
	private String name;
	private String field;
	private String withDatabase;
	private String withTable;
	private String withField;
	private String delete;
	private String update;
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
	public String getWithDatabase() {
		return withDatabase;
	}
	public void setWithDatabase(String withDatabase) {
		this.withDatabase = withDatabase;
	}
	public String getWithTable() {
		return withTable;
	}
	public void setWithTable(String withTable) {
		this.withTable = withTable;
	}
	public String getWithField() {
		return withField;
	}
	public void setWithField(String withField) {
		this.withField = withField;
	}
	public String getDelete() {
		return delete;
	}
	public void setDelete(String delete) {
		this.delete = delete;
	}
	public String getUpdate() {
		return update;
	}
	public void setUpdate(String update) {
		this.update = update;
	}
	@Override
	public String toString() {
		return "ForeignKey [name=" + name + ", field=" + field + ", withDatabase=" + withDatabase + ", withTable="
				+ withTable + ", withField=" + withField + ", delete=" + delete + ", update=" + update + "]";
	}
	
}
