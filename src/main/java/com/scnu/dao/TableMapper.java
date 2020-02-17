package com.scnu.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * ���ݼ�¼���� Mapper �ӿ�
 * @author chenrong
 *
 */
public interface TableMapper {
	
	   // ɾ�����ݼ�¼��Ҳ����ɾ��һ����¼
	   public int deleteRecord(@Param("database") String database, @Param("table") String table, @Param("oldRecord") Map<String, Object> oldRecord);
	   
	   // �������ݼ�¼
	   public int updateRecord(@Param("database") String database, @Param("table") String table, @Param("newRecord") Map<String, Object> newRecord, @Param("oldRecord") Map<String, Object> oldRecord);
	   
	   // �������ݼ�¼
	   public int insertRecord(@Param("database") String database, @Param("table") String table, @Param("newRecord") Map<String, Object> newRecord);
	   
	   // ��ѯ��ҳ���ݼ�¼��
	   public List<Map<String, Object>> selectRecords(@Param("database") String database, @Param("table") String table, @Param("offset") Integer offset, @Param("orderColumn") String orderColumn, @Param("orderType") String orderType, @Param("span") Integer span);
	   
	   // ��ѯ�������ݼ�¼��������
	   public Integer selectAllRecords(@Param("database") String database, @Param("table") String table);
	   
	   // �����ֶβ�ѯ��¼��
	   public List<Map<String, Object>> selectRecordsByColumn(@Param("database") String database, @Param("table") String table, @Param("columnName") String columnName, @Param("value") String value, @Param("offset") Integer offset, @Param("span") Integer span);
	   
	   // �����ֶβ�ѯ��¼����
	   public Integer selectAllRecordsByColumn(@Param("database") String database, @Param("table") String table, @Param("columnName") String columnName, @Param("value") String value);

}
