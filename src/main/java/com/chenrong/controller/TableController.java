package com.chenrong.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chenrong.bean.Const;
import com.chenrong.bean.Page;
import com.chenrong.bean.RecordSet;
import com.chenrong.bean.ScnuResult;
import com.chenrong.bean.TableVO;
import com.chenrong.service.TableService;
import com.zichang.service.TableInfoService;

@Controller
@RequestMapping("/record")
public class TableController {
	
	@Autowired
	TableService tableService;
	
	@Autowired
	TableInfoService tableInfoService;
	
	// 批量删除数据记录
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult deleteRecord(@RequestBody TableVO tableVO) {
		
		if(tableVO == null || tableVO.getConnectId() == null || tableVO.getDatabase() == null ||tableVO.getTable() == null || tableVO.getOldRecords() == null || tableVO.getOldRecords().size() == 0) {
			    return ScnuResult.forbidden("操作被拒绝，所请求的参数缺失错误");
		}
		try {
			// 若存在主键，则通过主键删除记录
			if(tableVO.getPrimaryKeys() != null) {
				generataPrimaryKeyMap(tableVO);
			}
			
			Integer affectRow = tableService.deleteRecord(tableVO);
			return ScnuResult.build("OK, Affected rows: " + affectRow);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ScnuResult.forbidden("服务器异常，删除记录失败");
		
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult updateRecord(@RequestBody TableVO tableVO) {
		
		
		if(tableVO == null || tableVO.getConnectId() == null || tableVO.getDatabase() == null ||
		   tableVO.getTable() == null || tableVO.getOldRecord() == null || tableVO.getNewRecord() == null) {
		    return ScnuResult.forbidden("操作被拒绝，所请求的参数缺失错误");
	    }
	    try {
		   // 若存在主键，则通过主键删除记录
		   if(tableVO.getPrimaryKeys() != null) {
			   generataPrimaryKeyMap(tableVO);
		    }
		
		    Integer affectRow = tableService.updateRecord(tableVO);
		    return ScnuResult.build("OK, Affected rows: " + affectRow);
	    } catch (Exception e) {
		   e.printStackTrace();
	    }
	    return ScnuResult.forbidden("服务器异常，更新记录失败");
	    
	    
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult insertRecord(@RequestBody TableVO tableVO) {
		
		if(tableVO == null || tableVO.getConnectId() == null || tableVO.getDatabase() == null ||tableVO.getTable() == null || tableVO.getNewRecord() == null) {
		    return ScnuResult.forbidden("操作被拒绝，所请求的参数缺失错误");
	    }
	    try {		
		    Integer affectRow = tableService.insertRecord(tableVO);
		    return ScnuResult.build("OK, Affected rows: " + affectRow);
	    } catch (Exception e) {
		   e.printStackTrace();
	    }
	    return ScnuResult.forbidden("主键重复，插入记录失败");
		
	}
	
	@RequestMapping(value = "/selectRecords", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult selectRecords(@RequestBody(required = false) TableVO tableVO) {

		System.out.println(tableVO.toString());
		if(tableVO == null || tableVO.getConnectId() == null || tableVO.getDatabase() == null ||tableVO.getTable() == null) {
		      return ScnuResult.forbidden("操作被拒绝，所请求的参数缺失错误");
	    }
		
		// 设置页码和排序方式
		if(tableVO.getCurrent() == null || tableVO.getCurrent() < 1) {
			// 默认首页
			tableVO.setCurrent(1);
		}
		if(tableVO.getOrderType() == null) {
			// 默认升序
			tableVO.setOrderType("asc");
		}
		
		try {
		   RecordSet recordSet = new RecordSet();
		   // 查询数据表的字段
		   List<String> columnsName = tableInfoService.showColumns(tableVO.getConnectId(), tableVO.getDatabase(), tableVO.getTable());
		   // 查询数据表的字段类型
		   Map<String, String> types = getFiledsType(tableVO.getConnectId(), tableVO.getDatabase(), tableVO.getTable());
		   // 查询数据表的主键
		   List<String> primaryKeys = tableInfoService.selectpk(tableVO.getConnectId(), tableVO.getDatabase(), tableVO.getTable());
		   // 查询数据表的记录集合
		   List<Map<String, Object>> records = tableService.selectRecords(tableVO);
		   // 组装页信息
		   Page page = new Page();
		   Integer current = tableVO.getCurrent();
		   page.setCurrent(current);
		   page.setPrePage(current - 1);
		   page.setRows(records.size());
		   //获取所有记录的数量
		   Integer allRows = tableService.getRecordsCount(tableVO);
		   page.setAllRows(allRows);
		   //设置尾页页码
		   page.setEnd(allRows);
		   //设置下一页，需要在确定end之后再确定下一页
		   page.setNextPage(current + 1);
		   
		   //设置页码列表， 最多七个元素
		   List<Integer> pageArr = new ArrayList();
		   Integer start = page.getStart();
		   Integer end = page.getEnd();
		   current = page.getCurrent();
		   
		   // 组装页码列表
		   // 小于pageNum
		   if(end - start < Const.pageNum) {
			   for(int i = start; i <= end; i++) {
				     pageArr.add(i);
			   }
		   }else {
			   // 直接从start开始设置页码
			   if(current - start < Const.pageNum/2) {
				     for(int i = start; i < (start + Const.pageNum); i++){
				    	     pageArr.add(i);
				     }
			   // 从	end末尾开始倒推    
			   }else if(end - current < Const.pageNum/2) {
				     for(int i = end - Const.pageNum + 1; i <= end; i++) {
				    	     pageArr.add(i);
				     } 
				   
			   }else {
				     for(int i = current - Const.pageNum/2; i <= current + Const.pageNum/2; i++) {
				    	     pageArr.add(i);
				     }
			   }	   
		   }
		   page.setPageArr(pageArr);
		   
		   //组装recordSet
		   recordSet.setPage(page);
		   recordSet.setColumnsName(columnsName);
		   recordSet.setTypes(types);
		   recordSet.setPrimaryKeys(primaryKeys);
		   recordSet.setRecords(records);
		   return ScnuResult.build(recordSet);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return ScnuResult.forbidden("服务器异常，查询失败");
		
	}
	
	@RequestMapping("/selectRecordsByColumn")
	@ResponseBody
	public ScnuResult selectRecord(String connectId, String database, String table, String columnName, String value, Integer current) {
		if(connectId == null || database == null || table == null || columnName == null || value == null) {
		      return ScnuResult.forbidden("操作被拒绝，所请求的参数缺失错误");
	    }
		
		// 默认第一页
		if(current == null || current < 1) {
			   current = 1;
		}
		
		try {
			RecordSet recordSet = new RecordSet();
			// 查询数据表的字段
			List<String> columnsName = tableInfoService.showColumns(connectId, database, table);
			// 查询数据表的字段类型
			Map<String, String> types = getFiledsType(connectId, database, table);
			// 查询数据表的主键
			List<String> primaryKeys = tableInfoService.selectpk(connectId, database, table);
			// 按照字段查询记录集
			List<Map<String, Object>> records = tableService.selectRecordsByColumn(connectId, database, table, columnName, value, current);
			
			// 处理分页
			 // 组装页信息
			   Page page = new Page();
			   page.setCurrent(current);
			   page.setPrePage(current - 1);
			   page.setRows(records.size());
			   //获取指定字段查询的元素总数
			   Integer allRows = tableService.selectAllRecordsByColumn(connectId, database, table, columnName, value);
			   page.setAllRows(allRows);
			   //设置尾页页码
			   page.setEnd(allRows);
			   //设置下一页，需要在确定end之后再确定下一页
			   page.setNextPage(current + 1);
			   
			   //设置页码列表， 最多七个元素
			   List<Integer> pageArr = new ArrayList();
			   Integer start = page.getStart();
			   Integer end = page.getEnd();
			   current = page.getCurrent();
			   
			   // 组装页码列表
			   // 小于pageNum
			   if(end - start < Const.pageNum) {
				   for(int i = start; i <= end; i++) {
					     pageArr.add(i);
				   }
			   }else {
				   // 直接从start开始设置页码
				   if(current - start < Const.pageNum/2) {
					     for(int i = start; i < (start + Const.pageNum); i++){
					    	     pageArr.add(i);
					     }
				   // 从	end末尾开始倒推    
				   }else if(end - current < Const.pageNum/2) {
					     for(int i = end - Const.pageNum + 1; i <= end; i++) {
					    	     pageArr.add(i);
					     } 
					   
				   }else {
					     for(int i = current - Const.pageNum/2; i <= current + Const.pageNum/2; i++) {
					    	     pageArr.add(i);
					     }
				   }	   
			   }
			   page.setPageArr(pageArr);
			   
			
			// 组装recordSet
			recordSet.setPage(page);
			recordSet.setColumnsName(columnsName);
			recordSet.setTypes(types);
			recordSet.setPrimaryKeys(primaryKeys);
			recordSet.setRecords(records);
			return ScnuResult.build(recordSet);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ScnuResult.forbidden("服务器异常，操作失败");
		
	}
	
	// 构建主键的Map
	private void generataPrimaryKeyMap(TableVO tableVO) {
		
		    // 构建单条旧记录的Map
		    if(tableVO.getOldRecord() != null) {
		          Map<String, Object> tempOldRecord = tableVO.getOldRecord();
		          Map<String, Object> oldRecord = new HashMap<String, Object>();
		          for(String key : tableVO.getPrimaryKeys()) {
			           oldRecord.put(key, tempOldRecord.get(key));
		          }
		          tableVO.setOldRecord(oldRecord);
		    }
		    
		    // 构建多条旧记录的Map
		    if(tableVO.getOldRecords() != null && tableVO.getOldRecords().size() != 0) {
		    	  List<Map<String, Object>> oldRecords = new ArrayList();
		          for(Map<String, Object> tempOldRecord : tableVO.getOldRecords()) {
		        	  Map<String, Object> oldRecord = new HashMap<String, Object>();
		        	  for(String key : tableVO.getPrimaryKeys()) {
				           oldRecord.put(key, tempOldRecord.get(key));
			          }
		        	  oldRecords.add(oldRecord);
		          }
		          tableVO.setOldRecords(oldRecords);
		    }
		    
	}
	
	// 得到字段的类型Map
	public Map<String, String> getFiledsType(String connectId, String database, String table){
		
		      Map<String, String> types = new HashMap();
		      try {
		    	  List<Map<String, Object>> lists = tableInfoService.showTableMsg(connectId, database, table);
		    	  if(lists != null) {
		    		   for(Map<String, Object> map : lists) {
		    			    String key = (String)map.get("Field");
		    			    String value = (String)map.get("Type");
		    			    if(value.equalsIgnoreCase("timestamp")) {
		    			    	value = "yyyy-MM-dd HH:mm:ss";
		    			    }
		    			    // 组装字段类型Map
		    			    types.put(key, value);
		    		   }
		    	  }
		      
		      } catch(Exception e) {
		    	  System.out.println("查询表结构异常");
		    	  e.printStackTrace();
		      }
		
		      return types;
	}

}
