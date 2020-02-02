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
	
	// ɾ���������ݼ�¼
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult deleteRecord(@RequestBody TableVO tableVO) {
		
		if(tableVO == null || tableVO.getConnectId() == null || tableVO.getDatabase() == null ||tableVO.getTable() == null || tableVO.getOldRecord() == null) {
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
	
	// ����ɾ�����ݼ�¼
	
	
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
	
	@RequestMapping("/selectRecords")
	@ResponseBody
	public ScnuResult selectRecords(@RequestBody TableVO tableVO) {
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
		   // ��ѯ���ݱ������
		   List<String> primaryKeys = tableInfoService.selectpk(tableVO.getConnectId(), tableVO.getDatabase(), tableVO.getTable());
		   // ��ѯ���ݱ�ļ�¼����
		   List<Map<String, Object>> records = tableService.selectRecords(tableVO);
		   // ��װҳ��Ϣ
		   Page page = new Page();
		   Integer current = tableVO.getCurrent();
		   page.setCurrent(current);
		   page.setPrePage(current - 1);
		   page.setNextPage(current + 1);
		   page.setRows(records.size());
		   //��ȡ���м�¼������
		   Integer allRows = tableService.getRecordsCount(tableVO);
		   //����βҳҳ��
		   page.setEnd(allRows);
		   
		   //��װrecordSet
		   recordSet.setPage(page);
		   recordSet.setColumnsName(columnsName);
		   recordSet.setPrimaryKeys(primaryKeys);
		   recordSet.setRecords(records);
		   return ScnuResult.build(recordSet);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return ScnuResult.forbidden("�������쳣����ѯʧ��");
		
	}
	
	@RequestMapping("/selectRecord")
	@ResponseBody
	public ScnuResult selectRecord(@RequestBody TableVO tableVO) {
		if(tableVO == null || tableVO.getConnectId() == null || tableVO.getDatabase() == null ||tableVO.getTable() == null) {
		      return ScnuResult.forbidden("�������ܾ���������Ĳ���ȱʧ����");
	    }
		
		try {
		    // ��������������ͨ���������Ҽ�¼
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
		
		return ScnuResult.forbidden("�������쳣������ʧ��");
		
	}
	
	// ����������Map
	private void generataPrimaryKeyMap(TableVO tableVO) {
		    Map<String, Object> tempOldRecord = tableVO.getOldRecord();
		    Map<String, Object> oldRecord = new HashMap<String, Object>();
		    for(String key : tableVO.getPrimaryKeys()) {
			      oldRecord.put(key, tempOldRecord.get(key));
		    }
		    tableVO.setOldRecord(oldRecord);
	}

}
