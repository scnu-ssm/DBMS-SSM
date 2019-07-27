package com.zichang.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zichang.bean.Fields;
import com.zichang.bean.ForeignKey;
import com.zichang.bean.Indexes;
import com.zichang.dao.TableInfoMapper;
import com.zichang.util.TableInfoTool;

@Service
public class TableInfoService {
	@Autowired
	TableInfoMapper tableInfoMapper;
	//查找数据库所有表名
	public List<String> showTables(String databaseName){
		List<String> tables = new ArrayList<String>();
		tables = tableInfoMapper.showTables(databaseName);
		return tables;
	}
	//删除表
	public int dropTable(String database, String table) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("database", database);
		map.put("table", table);
		int num = tableInfoMapper.dropTable(map);
		return num;
	}
	//清空表
	public int truncateTable(String database, String table) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("database", database);
		map.put("table", table);
		int num = tableInfoMapper.truncateTable(map);
		return num;
	}
	
	//创建表
	public int createTable(String database, String table, List<Fields> fields, List<Indexes> indexes, List<ForeignKey> foreignKeys) {
		int num = tableInfoMapper.createTable(database, table, fields, indexes, foreignKeys);
		return num;
	}
	
	//查询表字段
	public List<String> showColumns(String database, String table){
		List<String> list = new ArrayList<String>();
		list = tableInfoMapper.showColumns(database, table);
		return list;
	}
	
	//插入字段
	public String insertField(String database, String table, Fields field) {
		String message="";
		
		//判断字段是否已经存在
		List<String> list = tableInfoMapper.showColumns(database, table);
		int isExistField = 0;
		if(!list.isEmpty()) {
			for(int i = 0; i<list.size(); i++) {
				String str = list.get(i);
				if(str.equals(field.getName())) {
					isExistField = 1;
					break;
				}
			}
		}
		if(isExistField == 1) {
			message="field is existed";
		}else {
			tableInfoMapper.insertField(database, table, field);
			message="successfully";
		}
		return message;
	}
	
	//删除字段
	public String dropField(String database, String table, String field) {
		tableInfoMapper.dropField(database, table, field);
		return "successfully";
	}
	
	//修改字段
	public String changeField(String database, String table, String fieldName, Fields field) {
		tableInfoMapper.changeField(database, table, fieldName, field);
		return "successfully";
	}
	
	//查询表结构
	public List<Map<String, Object>> showTableMsg(String database, String table){
		List<Map<String, Object>> list = tableInfoMapper.showTableMsg(database, table);
		return list;
	}
	
	//重命名表
	public String renameTable(String database, String table, String rename) {
		tableInfoMapper.renameTable(database, table, rename);
		return "successfully";
	}
}
