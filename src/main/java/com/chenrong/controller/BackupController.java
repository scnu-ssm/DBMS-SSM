package com.chenrong.controller;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.chenrong.bean.ScnuResult;
import com.chenrong.service.BackupService;
import com.chenrong.util.DeleteTask;

/**
 *  �������SQL�ļ��ĵ����͵���
 * @author chenrong
 *
 */
@Controller
@RequestMapping("/mysql")
public class BackupController {
	
	@Autowired
	BackupService backupService;
	
	// ����ɾ����ʱ�ļ��Ķ�ʱ�̳߳�
	private ScheduledExecutorService pool = Executors.newScheduledThreadPool(15);
	
	// ���ݿ����� ����sql,���ļ����ص���ʽ����ǰ��
	@RequestMapping(value = "/backup", method=RequestMethod.POST)
	@ResponseBody
	public ScnuResult BackUp(String connectId, String database, @RequestParam("tables") List<String> tables, boolean isOnlyStructor, HttpServletResponse response) {
		
		if(tables == null || tables.size() == 0) {
			return ScnuResult.forbidden("�������κβ���");
		}
		
		try {
			String path = backupService.Backup(connectId, database, tables, isOnlyStructor);
			// ���������ļ�������
			response.setHeader("Content-Disposition", "attachment; filename=" + database + ".sql");
			OutputStream outputStream = response.getOutputStream();
			
			FileInputStream fis = new FileInputStream(path);
			int len = 0;
			byte[] bytes = new byte[2048];
			// ��ȡ��û������Ϊֹ
			while((len = fis.read(bytes)) > 0) {
				outputStream.write(bytes, 0, len);
			}
			fis.close();
			outputStream.close();
			
			// ɾ����ʱ�ļ������߳�
			DeleteTask task = new DeleteTask(path);
			// �첽����ɾ���ļ�
			pool.schedule(task, 10, TimeUnit.SECONDS);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ScnuResult.forbidden("ת��SQL�ļ�ʧ��");
		}
		
		return ScnuResult.build("ת��SQL�ļ��ɹ�");
		
	}
	
	
	// ��ԭ������ �ϴ�SQL�ļ�������ԭ��ָ�����ݿ�
	@RequestMapping(value = "/recovery", method = RequestMethod.POST)
	@ResponseBody
	public ScnuResult Recovery(String conenctId, String database, @RequestParam("sqlFile") MultipartFile sqlFile) {
		
		System.out.println("conenctId = " + conenctId);
		System.out.println("database = " + database);
		System.out.println("name = " + sqlFile.getName());
		
		Integer status = 0;
		try {
			status = backupService.Recovery(conenctId, database, sqlFile);
		} catch (Exception e) {
			e.printStackTrace();
			return ScnuResult.forbidden("�����쳣");
		}
		if(status.equals(-1)) {
			return ScnuResult.forbidden("����������");
		}else {
			return ScnuResult.build("��ԭ�ɹ�");
		}
		
	}
	
    // �������ݵ�csv�ļ���
    @RequestMapping(value = "/leadingOutCSV", method = RequestMethod.POST)
    @ResponseBody
    public ScnuResult leadingOutCSV(String connectId, String database, @RequestParam("tables") List<String> tables, HttpServletResponse response) {

    	if(connectId == null || database == null || tables == null || tables.size() == 0) {
    		     return ScnuResult.forbidden("����ȱʧ����������ʧ��");
    	}
    	
    	List<String> paths = new ArrayList();
    	try {
    	   // �������ű�ֱ�ӷ���csv�ļ���������
    	   if(tables.size() == 1) {
    		  String table = tables.get(0);
    		  String path = backupService.leadingOutCSV(connectId, database, table);
    		  
    		  // �����ļ��߼�
    		  response.setHeader("Content-Disposition", "attachment; filename=" + table + ".csv");
    		  FileInputStream fis = new FileInputStream(path);
    		  OutputStream outputStream = response.getOutputStream();
    		  int len = 0;
    		  byte[] bytes = new byte[2048];
    		  while((len = fis.read(bytes)) > 0) {
    			  outputStream.write(bytes, 0, len);
    		  }
    		  outputStream.close();
    		  fis.close();
    		  
    		  paths.add(path);
    	  }else{  // �������ű�ѹ��Ϊzip�ļ�,������
    		  
    		  response.setHeader("Content-Disposition", "attachment; filename=" + database + ".zip");
    		  OutputStream outputStream = response.getOutputStream();
    		  ZipOutputStream zos = new ZipOutputStream(outputStream);
    		  for(String table : tables) {
    			  String path = backupService.leadingOutCSV(connectId, database, table);
    			  ZipEntry zipEntry = new ZipEntry(table + ".csv");
    			  zos.putNextEntry(zipEntry);
    			  
    			  // ��csv�ļ�д��zos
    			  FileInputStream fis = new FileInputStream(path);
    			  int len = 0;
    			  byte[] bytes = new byte[2048];
    			  while((len = fis.read(bytes)) > 0) {
    				  zos.write(bytes, 0, len);
    			  }
    			  fis.close();
    			  
    			  paths.add(path);
    		  }
    		  // �ر�zip��
    		  zos.close();
    	  }

          // �����ʱ�ļ�
    	   DeleteTask task = new DeleteTask(paths);
    	   pool.schedule(task, 10, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
			return ScnuResult.forbidden("�����������쳣����");
		}
    	
    	return ScnuResult.build("�������ݵ�CSV�ļ��ɹ�");
    }
    
    // ���ݿ������ݴ���ģ�鷽ʽ2,���ش���ɹ���ʧ�ܵ��������Ϊ��ʱ
    @RequestMapping(value = "dataTransfer2", method = RequestMethod.POST)
    @ResponseBody
    public ScnuResult DataTransfer2(String srcConnectId, String srcDatabase, @RequestParam("tables") List<String> tables, String destconnectId, String destDatabase, boolean isOnlyStructor) {
    	// �����������ݱ�����ʧ��
    	if(tables == null || tables.size() == 0) {
    		  return ScnuResult.forbidden("û�н����κβ���");
    	}
    	List<String> success = new ArrayList();
    	List<String> failure = new ArrayList();
    	// ���������ʧ�ܷŵ�ʧ�ܵļ��ϣ��ɹ��ŵ��ɹ��ļ���
    	for(String table : tables) {
    		try {
    			// ����
        		String path = backupService.BackupTable(srcConnectId, srcDatabase, table, isOnlyStructor);
        		// ��������
        		backupService.Recovery(destconnectId, destDatabase, path);
        		
        		// ɾ����ʱ�ļ������߳�
        		DeleteTask task = new DeleteTask(path);
        		// �첽����ɾ���ļ�
        		pool.schedule(task, 10, TimeUnit.SECONDS);
        		
    		}catch(Exception e) {
    			failure.add(table);
    			System.out.println("���ݴ������");
    			e.printStackTrace();
    			continue;
    		}
    		    success.add(table);
    	}
    	Map<String, List<String>> map = new HashMap();
    	map.put("success", success);
    	map.put("failure", failure);
    	return ScnuResult.build(map);
    }
    
    // ���ݿ������ݴ���ģ�鷽ʽ����ʱ��
    @RequestMapping(value = "dataTransfer", method = RequestMethod.POST)
    @ResponseBody
    public ScnuResult DataTransfer(String srcConnectId, String srcDatabase, @RequestParam("tables") List<String> tables, String destconnectId, String destDatabase, boolean isOnlyStructor) {
    	// �����������ݱ�����ʧ��
    	if(tables == null || tables.size() == 0) {
    		  return ScnuResult.forbidden("û�н����κβ���");
    	}
        System.out.println("tables : ");
        for(String table : tables) {
        	 System.out.println(table);
        }
    	try {
    	// ��������
        String path = backupService.Backup(srcConnectId, srcDatabase, tables, isOnlyStructor);
        // ��������
        backupService.Recovery(destconnectId, destDatabase, path);
        
		// ɾ����ʱ�ļ������߳�
		DeleteTask task = new DeleteTask(path);
		// �첽����ɾ���ļ�
		pool.schedule(task, 10, TimeUnit.SECONDS);
		
    	}catch(Exception e) {
    		e.printStackTrace();
    		return ScnuResult.forbidden("���ݴ���ʧ��");
    	}
    		 
    	return ScnuResult.build("���ݴ���ɹ�");
    }
    
    
 
}
