package com.scnu.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zichang.bean.Fields;
import com.zichang.bean.ForeignKey;
import com.zichang.bean.Indexes;

public interface TableInfoMapper {
	//����ĳ���ݿ����б���
	List<String> showTables(String databaseName);
	
	//ɾ���� map={[database,name],[table,name]}
	int dropTable(Map<String, String> map);
	
	//��ձ�  map={[database,name],[table,name]}
	int truncateTable(Map<String, String> map);
	
//	//������
//	int createTable(@Param("database")String database, 
//					@Param("table")String table,
//					@Param("fields")List<Fields> fields, //�ֶ�
//					@Param("indexes")List<Indexes> indexes, //����
//					@Param("foreignkeys")List<ForeignKey> foreignkeys);//���
	int createTable(@Param("database")String database, @Param("table")String table, @Param("fields")List<Fields> fields, @Param("indexes")List<Indexes> indexes, @Param("foreignkeys")List<ForeignKey> foreignkeys);
	
	List<String> showColumns(@Param("database")String database, @Param("table")String table);
	
	int insertField(@Param("database")String database, @Param("table")String table,@Param("field")Fields field);
	
	int dropField(@Param("database")String database, @Param("table")String table,@Param("field")String field);
	
	int changeField(@Param("database")String database, @Param("table")String table,@Param("fieldName")String fieldName,@Param("field")Fields field);
	
	List<Map<String, Object>> showTableMsg(@Param("database")String database, @Param("table")String table);
	
	int renameTable(@Param("database")String database, @Param("table")String table, @Param("rename")String rename);
}
