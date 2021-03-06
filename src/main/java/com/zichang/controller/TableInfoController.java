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
import com.chenrong.bean.ScnuResult;
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
	public ScnuResult showTables(String connectId, String databaseName) {
		List<String> tables = new ArrayList<String>();
		try {
			tables = tableInfoService.showTables(connectId,databaseName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ScnuResult.build(tables);
	}
	
	//删除表
	@RequestMapping(value = "/droptable", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult dropTable(String connectId, String database, String table) {
		try {
			if (tableInfoService.dropTable(connectId, database, table) >= 0) {
				return ScnuResult.build("删除表成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ScnuResult.buildFalure("删除表失败");
	}
	
	//清空表
	@RequestMapping(value = "/truncatetable", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult truncateTable(String connectId, String database, String table) {
		try {
			if (tableInfoService.truncateTable(connectId, database, table) >= 0) {
				return ScnuResult.build("清空表成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ScnuResult.buildFalure("清空表失败");
	}
	
	//创建表
	@RequestMapping(value = "/createtable", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult createTable(
//							   String database, 
//							   String table,
//							   List<Fields> fields,
//							   List<Indexes> indexes,
//							   List<ForeignKey> foreignKeys, 
							   @RequestBody String jsonStr) {
//		System.out.println("json = " + jsonStr);
		JSONObject json = JSONObject.parseObject(jsonStr);
		JSONArray arr = json.getJSONArray("fields");
//		JSONArray arr2 = json.getJSONArray("indexes");
		JSONArray arr3 = json.getJSONArray("foreignKeys");
		String db = json.getString("database");
		String tb = json.getString("table");
		String connectId = json.getString("connectId");
		List<Fields> fieldsList = new ArrayList<Fields>();
		fieldsList = TableInfoTool.getFieldList(arr);
//		List<Indexes> indexesList = new ArrayList<Indexes>();
//		indexesList = TableInfoTool.getIndexesList(arr2);
		List<ForeignKey> foreignKeyList = new ArrayList<ForeignKey>();
		foreignKeyList = TableInfoTool.getForeignkeyList(arr3);
//		System.out.println(db);
//		System.out.println(tb);
//		System.out.println(fieldsList.get(0).toString());
//		System.out.println(indexesList.get(0).toString());
//		System.out.println(foreignKeyList.get(0).toString());
		try {
			if (tableInfoService.createTable(connectId, db, tb, fieldsList, foreignKeyList) >= 0) {
				return ScnuResult.build("创建表成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ScnuResult.buildFalure("创建表失败");

	}
	
	//查询表字段
	@RequestMapping(value = "/showcolumns", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult showColumns(String connectId, String database, String table){
		List<String> list = new ArrayList<String>();
		try {
			list = tableInfoService.showColumns(connectId, database, table);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ScnuResult.build(list);
	}
	
	//插入字段
	@RequestMapping(value = "/insertfield", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult insertField(@RequestBody String jsonStr) {
		String message = "";
		JSONObject json = JSONObject.parseObject(jsonStr);
		String db = json.getString("database");
		String tb = json.getString("table");
		String connectId = json.getString("connectId");
		
		Fields f = new Fields();
		JSONObject jsonObject = json.getJSONObject("field");
		f.setName(jsonObject.getString("name"));
		f.setLength(jsonObject.getString("length"));
		f.setType(jsonObject.getString("type"));
		f.setNotNull(jsonObject.getIntValue("notNull"));
		f.setPrimary(jsonObject.getIntValue("primary"));
		
//		System.out.println(db + " " + tb + " " + f.toString());
		try {
			return tableInfoService.insertField(connectId, db, tb, f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ScnuResult.buildFalure("插入字段失败");
	}
	
	//删除字段
	@RequestMapping(value = "/dropfield", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult dropField(String connectId,
						    String database,
							String table,
							String field){
		try {
			if(tableInfoService.dropField(connectId, database, table, field) >= 0)
				return ScnuResult.build("删除字段成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ScnuResult.buildFalure("删除字段失败");
	}
	
	//修改字段
	@RequestMapping(value = "/changefield", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult changeField(@RequestBody String jsonStr) {
		JSONObject json = JSONObject.parseObject(jsonStr);
		String connectId = json.getString("connectId");
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
		
		try {
			if(tableInfoService.changeField(connectId, db, tb, fname, f) >= 0)
				return ScnuResult.build("修改字段成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ScnuResult.buildFalure("修改字段失败");

	}
	
	//查询表结构
	@RequestMapping(value = "/showtablemsg", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult showTableMsg(String connectId, String database, String table){
		List<Map<String, Object>> list = new ArrayList<>();
		try {
			list = tableInfoService.showTableMsg(connectId, database, table);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ScnuResult.build(list);
	}
	
	//重命名表
	@RequestMapping(value = "/renametable", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult renameTable(String connectId, String database, String table, String rename){
		try {
			if(tableInfoService.renameTable(connectId, database, table, rename) >= 0)
				return ScnuResult.build("重命名字段成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ScnuResult.buildFalure("重命名字段失败");
	}
	
	//查询外键
	@RequestMapping(value="/allfk", method=RequestMethod.POST)
	@ResponseBody
	public ScnuResult allfk(String connectId, String database, String table) {
		List<ForeignKey> list = new ArrayList<>();
		try {
			list = tableInfoService.allfk(connectId, database, table);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ScnuResult.build(list);
	}
	
	//删除外键
	@RequestMapping(value="/deletefk", method=RequestMethod.POST)
	@ResponseBody
	public ScnuResult deletefk(String connectId, String database, String table, String fname) {
		try {
			if(tableInfoService.deletefk(connectId, database, table, fname) >= 0)
				return ScnuResult.build("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ScnuResult.buildFalure("删除失败");
	}
	
	//添加外键
	@RequestMapping(value="/insertfk", method=RequestMethod.POST)
	@ResponseBody
	public ScnuResult insertfk(@RequestBody String jsonStr) {
		JSONObject json = JSONObject.parseObject(jsonStr);
		String connectId = json.getString("connectId");
		String database = json.getString("database");
		String table = json.getString("table");
		ForeignKey fk = json.getObject("fk", ForeignKey.class);
//		System.out.println(fk.toString());
		try {
			if(tableInfoService.insertfk(connectId, database, table, fk) >= 0)
				return ScnuResult.build("添加外键成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ScnuResult.buildFalure("添加外键失败");
	}
	
	//查询主键
	@RequestMapping(value="/selectpk", method=RequestMethod.POST)
	@ResponseBody
	public ScnuResult selectpk(String connectId, String database, String table) {
		List<String> list = new ArrayList<>();
		try {
			list = tableInfoService.selectpk(connectId, database, table);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ScnuResult.build(list);
	}
	
	//设置主键
	@RequestMapping(value="/setpk", method=RequestMethod.POST)
	@ResponseBody
	public ScnuResult setpk(String connectId, String database, String table, String field) {
		int i = -1;
		try {
			i = tableInfoService.setpk(connectId, database, table, field);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println("主键重复返回值i="+i);
		if(i >= 0) {
			return ScnuResult.build("设置主键成功");
		}else {
			return ScnuResult.buildFalure("主键唯一");
		}
	}
	
	//删除主键
	@RequestMapping(value="/deletepk", method=RequestMethod.POST)
	@ResponseBody
	public ScnuResult deletepk(String connectId, String database, String table) {
		try {
			if(tableInfoService.deletepk(connectId, database, table) >= 0)
				return ScnuResult.build("删除主键成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ScnuResult.buildFalure("删除主键失败");
	}
}
