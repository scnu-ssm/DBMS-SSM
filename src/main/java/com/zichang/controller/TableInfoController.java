package com.zichang.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;
import com.zichang.bean.Fields;
import com.zichang.bean.ForeignKey;
import com.zichang.bean.Indexes;
import com.zichang.service.TableInfoService;
import com.zichang.util.TableInfoTool;

@Controller
@RequestMapping("/tableinfo")
public class TableInfoController {
	
	@Autowired
	TableInfoService tableInfoService;
	
	//查找数据库所有表名，返回给前端
	@RequestMapping(value = "/showtables", method = RequestMethod.POST)
	@ResponseBody
	public String showTables(@RequestParam(value = "databaseName",required = false) String databaseName) {
		List<String> tables = new ArrayList<String>();
		tables = tableInfoService.showTables(databaseName);
		String s="";
		if(!tables.isEmpty())
			s = databaseName+":"+tables.toString();
		else {
			s = "database not exist!";
		}
		return s;
	}
	
	//删除表
	@RequestMapping(value = "/droptable", method = RequestMethod.POST)
	@ResponseBody
	public String dropTable(@RequestParam(value = "database",required = false) String database, @RequestParam(value = "table",required = false) String table) {
		int num = 1;
		num = tableInfoService.dropTable(database, table);
		String s = "";
		if(num==0) {
			s = "successfully DROP TABLE "+table;
		}else {
			s = "failed DROP TABLE";
		}
		return s;
	}
	
	//清空表
	@RequestMapping(value = "/truncatetable", method = RequestMethod.POST)
	@ResponseBody
	public String truncateTable(@RequestParam(value = "database",required = false) String database, @RequestParam(value = "table",required = false) String table) {
		int num = 1;
		num = tableInfoService.truncateTable(database, table);
		String s = "";
		if(num==0) {
			s = "successfully TRUNCATE TABLE "+table;
		}else {
			s = "failed TRUNCATE TABLE";
		}
		return s;
	}
	
	//创建表
	@RequestMapping(value = "/createtable", method = RequestMethod.POST)
	@ResponseBody
	public String createTable( @RequestParam(value = "database",required = false) String database, 
							   @RequestParam(value = "table",required = false) String table,
							   @RequestParam(value = "fields",required = false) List<Fields> fields,
							   @RequestParam(value = "indexes",required = false) List<Indexes> indexes,
							   @RequestParam(value = "foreignKeys",required = false) List<ForeignKey> foreignKeys, @RequestBody String jsonStr) {
//		System.out.println("json = " + jsonStr);
		JSONObject json = JSONObject.parseObject(jsonStr);
		JSONArray arr = json.getJSONArray("fields");
		JSONArray arr2 = json.getJSONArray("indexes");
		JSONArray arr3 = json.getJSONArray("foreignKeys");
		String db = json.getString("database");
		String tb = json.getString("table");
		List<Fields> fieldsList = new ArrayList<Fields>();
		fieldsList = TableInfoTool.getFieldList(arr);
		List<Indexes> indexesList = new ArrayList<Indexes>();
		indexesList = TableInfoTool.getIndexesList(arr2);
		List<ForeignKey> foreignKeyList = new ArrayList<ForeignKey>();
		foreignKeyList = TableInfoTool.getForeignkeyList(arr3);
//		System.out.println(db);
//		System.out.println(tb);
//		System.out.println(fieldsList.get(0).toString());
//		System.out.println(indexesList.get(0).toString());
//		System.out.println(foreignKeyList.get(0).toString());
		int num = 1;
		num = tableInfoService.createTable(db, tb, fieldsList, indexesList, foreignKeyList);
		String s = "";
		if(num == 0) {
			s = "successfully create table " + table;
		}else {
			s = "failed to create table";
		}
		return s;
//		return null;
	}
	
	//查询表字段
	@RequestMapping(value = "/showcolumns", method = RequestMethod.POST)
	@ResponseBody
	public List<String> showColumns(@RequestParam(value="database",required = false)String database, @RequestParam(value="table",required = false)String table){
		List<String> list = new ArrayList<String>();
		list = tableInfoService.showColumns(database, table);
//		System.out.println(list);
		return list;
	}
	
	//插入字段
	@RequestMapping(value = "/insertfield", method = RequestMethod.POST)
	@ResponseBody
	public String insertField(@RequestBody String jsonStr) {
		String message = "";
		JSONObject json = JSONObject.parseObject(jsonStr);
		String db = json.getString("database");
		String tb = json.getString("table");
		
		Fields f = new Fields();
		JSONObject jsonObject = json.getJSONObject("field");
		f.setName(jsonObject.getString("name"));
		f.setLength(jsonObject.getString("length"));
		f.setType(jsonObject.getString("type"));
		f.setNotNull(jsonObject.getIntValue("notNull"));
		f.setPrimary(jsonObject.getIntValue("primary"));
		
//		System.out.println(db + " " + tb + " " + f.toString());
		message = tableInfoService.insertField(db, tb, f);
		
		return message;
	}
	
	//删除字段
	@RequestMapping(value = "/dropfield", method = RequestMethod.POST)
	@ResponseBody
	public String dropField(@RequestParam(value="database")String database,
							@RequestParam(value="table")String table,
							@RequestParam(value="field")String field){
		String message = "";
		message = tableInfoService.dropField(database, table, field);
		return message;
	}
	
	//修改字段
	@RequestMapping(value = "/changefield", method = RequestMethod.POST)
	@ResponseBody
	public String changeField(@RequestBody String jsonStr) {
		JSONObject json = JSONObject.parseObject(jsonStr);
		String db = json.getString("database");
		String tb = json.getString("table");
		String fname = json.getString("fieldName");
		JSONObject jsonObject = json.getJSONObject("field");
		
		Fields f = new Fields();
		f.setName(jsonObject.getString("name"));
		f.setLength(jsonObject.getString("length"));
		f.setType(jsonObject.getString("type"));
		f.setNotNull(jsonObject.getIntValue("notNull"));
		f.setPrimary(jsonObject.getIntValue("primary"));
		
		String message = "";
		message = tableInfoService.changeField(db, tb, fname, f);
		return message;
	}
	
	//查询表结构
	@RequestMapping(value = "/showtablemsg", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> showTableMsg(@RequestParam(value="database")String database,@RequestParam(value="table")String table){
		List<Map<String, Object>> list = tableInfoService.showTableMsg(database, table);
		return list;
	}
	
	//重命名表
	@RequestMapping(value = "/renametable", method = RequestMethod.POST)
	@ResponseBody
	public String renameTable(@RequestParam(value="database")String database,@RequestParam(value="table")String table,@RequestParam(value="rename")String rename){
		String msg = tableInfoService.renameTable(database, table, rename);
		return msg;
	}
}
