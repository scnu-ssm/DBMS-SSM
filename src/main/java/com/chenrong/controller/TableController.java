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
	
	// ����ɾ�����ݼ�¼
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult deleteRecord(@RequestBody TableVO tableVO) {
		
		if(tableVO == null || tableVO.getConnectId() == null || tableVO.getDatabase() == null ||tableVO.getTable() == null || tableVO.getOldRecords() == null || tableVO.getOldRecords().size() == 0) {
			    return ScnuResult.forbidden("�������ܾ���������Ĳ���ȱʧ����");
		}
		try {
			// ��������������ͨ������ɾ����¼
			if(tableVO.getPrimaryKeys() != null) {
				generataPrimaryKeyMap(tableVO);
			}
			
			Integer affectRow = tableService.deleteRecord(tableVO);
			return ScnuResult.build("OK, Affected rows: " + affectRow);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ScnuResult.forbidden("�������쳣��ɾ����¼ʧ��");
		
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult updateRecord(@RequestBody TableVO tableVO) {
		
		
		if(tableVO == null || tableVO.getConnectId() == null || tableVO.getDatabase() == null ||
		   tableVO.getTable() == null || tableVO.getOldRecord() == null || tableVO.getNewRecord() == null) {
		    return ScnuResult.forbidden("�������ܾ���������Ĳ���ȱʧ����");
	    }
	    try {
		   // ��������������ͨ������ɾ����¼
		   if(tableVO.getPrimaryKeys() != null) {
			   generataPrimaryKeyMap(tableVO);
		    }
		
		    Integer affectRow = tableService.updateRecord(tableVO);
		    return ScnuResult.build("OK, Affected rows: " + affectRow);
	    } catch (Exception e) {
		   e.printStackTrace();
	    }
	    return ScnuResult.forbidden("�������쳣�����¼�¼ʧ��");
	    
	    
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult insertRecord(@RequestBody TableVO tableVO) {
		
		if(tableVO == null || tableVO.getConnectId() == null || tableVO.getDatabase() == null ||tableVO.getTable() == null || tableVO.getNewRecord() == null) {
		    return ScnuResult.forbidden("�������ܾ���������Ĳ���ȱʧ����");
	    }
	    try {		
		    Integer affectRow = tableService.insertRecord(tableVO);
		    return ScnuResult.build("OK, Affected rows: " + affectRow);
	    } catch (Exception e) {
		   e.printStackTrace();
	    }
	    return ScnuResult.forbidden("�����ظ��������¼ʧ��");
		
	}
	
	@RequestMapping(value = "/selectRecords", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult selectRecords(@RequestBody(required = false) TableVO tableVO) {

		System.out.println(tableVO.toString());
		if(tableVO == null || tableVO.getConnectId() == null || tableVO.getDatabase() == null ||tableVO.getTable() == null) {
		      return ScnuResult.forbidden("�������ܾ���������Ĳ���ȱʧ����");
	    }
		
		// ����ҳ�������ʽ
		if(tableVO.getCurrent() == null || tableVO.getCurrent() < 1) {
			// Ĭ����ҳ
			tableVO.setCurrent(1);
		}
		if(tableVO.getOrderType() == null) {
			// Ĭ������
			tableVO.setOrderType("asc");
		}
		
		try {
		   RecordSet recordSet = new RecordSet();
		   // ��ѯ���ݱ���ֶ�
		   List<String> columnsName = tableInfoService.showColumns(tableVO.getConnectId(), tableVO.getDatabase(), tableVO.getTable());
		   // ��ѯ���ݱ���ֶ�����
		   Map<String, String> types = getFiledsType(tableVO.getConnectId(), tableVO.getDatabase(), tableVO.getTable());
		   // ��ѯ���ݱ������
		   List<String> primaryKeys = tableInfoService.selectpk(tableVO.getConnectId(), tableVO.getDatabase(), tableVO.getTable());
		   // ��ѯ���ݱ�ļ�¼����
		   List<Map<String, Object>> records = tableService.selectRecords(tableVO);
		   // ��װҳ��Ϣ
		   Page page = new Page();
		   Integer current = tableVO.getCurrent();
		   page.setCurrent(current);
		   page.setPrePage(current - 1);
		   page.setRows(records.size());
		   //��ȡ���м�¼������
		   Integer allRows = tableService.getRecordsCount(tableVO);
		   page.setAllRows(allRows);
		   //����βҳҳ��
		   page.setEnd(allRows);
		   //������һҳ����Ҫ��ȷ��end֮����ȷ����һҳ
		   page.setNextPage(current + 1);
		   
		   //����ҳ���б� ����߸�Ԫ��
		   List<Integer> pageArr = new ArrayList();
		   Integer start = page.getStart();
		   Integer end = page.getEnd();
		   current = page.getCurrent();
		   
		   // ��װҳ���б�
		   // С��pageNum
		   if(end - start < Const.pageNum) {
			   for(int i = start; i <= end; i++) {
				     pageArr.add(i);
			   }
		   }else {
			   // ֱ�Ӵ�start��ʼ����ҳ��
			   if(current - start < Const.pageNum/2) {
				     for(int i = start; i < (start + Const.pageNum); i++){
				    	     pageArr.add(i);
				     }
			   // ��	endĩβ��ʼ����    
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
		   
		   //��װrecordSet
		   recordSet.setPage(page);
		   recordSet.setColumnsName(columnsName);
		   recordSet.setTypes(types);
		   recordSet.setPrimaryKeys(primaryKeys);
		   recordSet.setRecords(records);
		   return ScnuResult.build(recordSet);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return ScnuResult.forbidden("�������쳣����ѯʧ��");
		
	}
	
	@RequestMapping("/selectRecordsByColumn")
	@ResponseBody
	public ScnuResult selectRecord(String connectId, String database, String table, String columnName, String value, Integer current) {
		if(connectId == null || database == null || table == null || columnName == null || value == null) {
		      return ScnuResult.forbidden("�������ܾ���������Ĳ���ȱʧ����");
	    }
		
		// Ĭ�ϵ�һҳ
		if(current == null || current < 1) {
			   current = 1;
		}
		
		try {
			RecordSet recordSet = new RecordSet();
			// ��ѯ���ݱ���ֶ�
			List<String> columnsName = tableInfoService.showColumns(connectId, database, table);
			// ��ѯ���ݱ���ֶ�����
			Map<String, String> types = getFiledsType(connectId, database, table);
			// ��ѯ���ݱ������
			List<String> primaryKeys = tableInfoService.selectpk(connectId, database, table);
			// �����ֶβ�ѯ��¼��
			List<Map<String, Object>> records = tableService.selectRecordsByColumn(connectId, database, table, columnName, value, current);
			
			// �����ҳ
			 // ��װҳ��Ϣ
			   Page page = new Page();
			   page.setCurrent(current);
			   page.setPrePage(current - 1);
			   page.setRows(records.size());
			   //��ȡָ���ֶβ�ѯ��Ԫ������
			   Integer allRows = tableService.selectAllRecordsByColumn(connectId, database, table, columnName, value);
			   page.setAllRows(allRows);
			   //����βҳҳ��
			   page.setEnd(allRows);
			   //������һҳ����Ҫ��ȷ��end֮����ȷ����һҳ
			   page.setNextPage(current + 1);
			   
			   //����ҳ���б� ����߸�Ԫ��
			   List<Integer> pageArr = new ArrayList();
			   Integer start = page.getStart();
			   Integer end = page.getEnd();
			   current = page.getCurrent();
			   
			   // ��װҳ���б�
			   // С��pageNum
			   if(end - start < Const.pageNum) {
				   for(int i = start; i <= end; i++) {
					     pageArr.add(i);
				   }
			   }else {
				   // ֱ�Ӵ�start��ʼ����ҳ��
				   if(current - start < Const.pageNum/2) {
					     for(int i = start; i < (start + Const.pageNum); i++){
					    	     pageArr.add(i);
					     }
				   // ��	endĩβ��ʼ����    
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
			   
			
			// ��װrecordSet
			recordSet.setPage(page);
			recordSet.setColumnsName(columnsName);
			recordSet.setTypes(types);
			recordSet.setPrimaryKeys(primaryKeys);
			recordSet.setRecords(records);
			return ScnuResult.build(recordSet);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ScnuResult.forbidden("�������쳣������ʧ��");
		
	}
	
	// ����������Map
	private void generataPrimaryKeyMap(TableVO tableVO) {
		
		    // ���������ɼ�¼��Map
		    if(tableVO.getOldRecord() != null) {
		          Map<String, Object> tempOldRecord = tableVO.getOldRecord();
		          Map<String, Object> oldRecord = new HashMap<String, Object>();
		          for(String key : tableVO.getPrimaryKeys()) {
			           oldRecord.put(key, tempOldRecord.get(key));
		          }
		          tableVO.setOldRecord(oldRecord);
		    }
		    
		    // ���������ɼ�¼��Map
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
	
	// �õ��ֶε�����Map
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
		    			    // ��װ�ֶ�����Map
		    			    types.put(key, value);
		    		   }
		    	  }
		      
		      } catch(Exception e) {
		    	  System.out.println("��ѯ��ṹ�쳣");
		    	  e.printStackTrace();
		      }
		
		      return types;
	}

}
