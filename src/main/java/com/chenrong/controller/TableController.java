package com.chenrong.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	// 删除单条数据记录
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult deleteRecord(@RequestBody TableVO tableVO) {
		
		if(tableVO == null || tableVO.getConnectId() == null || tableVO.getDatabase() == null ||tableVO.getTable() == null || tableVO.getOldRecord() == null) {
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
	
	// 批量删除数据记录
	
	
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
	
	@RequestMapping("/selectRecords")
	@ResponseBody
	public ScnuResult selectRecords(@RequestBody TableVO tableVO) {
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
		   // 查询数据表的主键
		   List<String> primaryKeys = tableInfoService.selectpk(tableVO.getConnectId(), tableVO.getDatabase(), tableVO.getTable());
		   // 查询数据表的记录集合
		   List<Map<String, Object>> records = tableService.selectRecords(tableVO);
		   // 组装页信息
		   Page page = new Page();
		   Integer current = tableVO.getCurrent();
		   page.setCurrent(current);
		   page.setPrePage(current - 1);
		   page.setNextPage(current + 1);
		   page.setRows(records.size());
		   //获取所有记录的数量
		   Integer allRows = tableService.getRecordsCount(tableVO);
		   //设置尾页页码
		   page.setEnd(allRows);
		   
		   //组装recordSet
		   recordSet.setPage(page);
		   recordSet.setColumnsName(columnsName);
		   recordSet.setPrimaryKeys(primaryKeys);
		   recordSet.setRecords(records);
		   return ScnuResult.build(recordSet);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return ScnuResult.forbidden("服务器异常，查询失败");
		
	}
	
	@RequestMapping("/selectRecord")
	@ResponseBody
	public ScnuResult selectRecord(@RequestBody TableVO tableVO) {
		if(tableVO == null || tableVO.getConnectId() == null || tableVO.getDatabase() == null ||tableVO.getTable() == null) {
		      return ScnuResult.forbidden("操作被拒绝，所请求的参数缺失错误");
	    }
		
		try {
		    // 若存在主键，则通过主键查找记录
		    if(tableVO.getPrimaryKeys() != null) {
				   generataPrimaryKeyMap(tableVO);
			}
			Map<String, Object> record = tableService.selectRecord(tableVO);
			Map<String, Map<String, Object>> map = new HashMap();
			map.put("record", record);
			return ScnuResult.build(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ScnuResult.forbidden("服务器异常，操作失败");
		
	}
	
	// 构建主键的Map
	private void generataPrimaryKeyMap(TableVO tableVO) {
		    Map<String, Object> tempOldRecord = tableVO.getOldRecord();
		    Map<String, Object> oldRecord = new HashMap<String, Object>();
		    for(String key : tableVO.getPrimaryKeys()) {
			      oldRecord.put(key, tempOldRecord.get(key));
		    }
		    tableVO.setOldRecord(oldRecord);
	}

}
