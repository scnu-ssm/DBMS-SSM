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
	   
	   // ��ѯ���ݼ�¼��
	   public List<Map<String, Object>> selectRecords(@Param("database") String database, @Param("table") String table);
	   
	   // ��ѯ�������ݼ�¼
	   public Map<String, Object> selectRecord(@Param("database") String database, @Param("table") String table, @Param("oldRecord") Map<String, Object> oldRecord);

}
