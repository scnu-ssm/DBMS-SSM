package com.scnu.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zichang.bean.Fields;
import com.zichang.bean.ForeignKey;
import com.zichang.bean.Indexes;

public interface TableInfoMapper {
	//查找某数据库所有表名
	List<String> showTables(String databaseName);
	
	//删除表 map={[database,name],[table,name]}
	int dropTable(Map<String, String> map);
	
	//清空表  map={[database,name],[table,name]}
	int truncateTable(Map<String, String> map);
	
//	//创建表
//	int createTable(@Param("database")String database, 
//					@Param("table")String table,
//					@Param("fields")List<Fields> fields, //字段
//					@Param("indexes")List<Indexes> indexes, //索引
//					@Param("foreignkeys")List<ForeignKey> foreignkeys);//外键
	int createTable(@Param("database")String database, @Param("table")String table, @Param("fields")List<Fields> fields,  @Param("foreignkeys")List<ForeignKey> foreignkeys);
	
	List<String> showColumns(@Param("database")String database, @Param("table")String table);
	
	int insertField(@Param("database")String database, @Param("table")String table,@Param("field")Fields field);
	
	int dropField(@Param("database")String database, @Param("table")String table,@Param("field")String field);
	
	int changeField(@Param("database")String database, @Param("table")String table,@Param("fieldName")String fieldName,@Param("field")Fields field);
	
	List<Map<String, Object>> showTableMsg(@Param("database")String database, @Param("table")String table);
	
	int renameTable(@Param("database")String database, @Param("table")String table, @Param("rename")String rename);
	
	//查询表外键
	List<ForeignKey> allfk(@Param("database")String database, @Param("table")String table);
	
	//删除外键
	int deletefk(@Param("database")String database, @Param("table")String table, @Param("fname")String fname);
	
	//添加外键
	int insertfk(@Param("database")String database, @Param("table")String table, @Param("fk")ForeignKey fk);
	
	//查询主键
	List<String> selectpk(@Param("database")String database, @Param("table")String table);
	
	//设置主键
	int setpk(@Param("database")String database, @Param("table")String table, @Param("field")String field);
	
	//取消主键
	int deletepk(@Param("database")String database, @Param("table")String table);
}
