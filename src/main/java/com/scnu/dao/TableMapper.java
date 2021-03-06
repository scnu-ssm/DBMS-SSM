package com.scnu.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 数据记录操作 Mapper 接口
 * @author chenrong
 *
 */
public interface TableMapper {
	
	   // 删除数据记录，也仅能删除一条记录
	   public int deleteRecord(@Param("database") String database, @Param("table") String table, @Param("oldRecord") Map<String, Object> oldRecord);
	   
	   // 更新数据记录
	   public int updateRecord(@Param("database") String database, @Param("table") String table, @Param("newRecord") Map<String, Object> newRecord, @Param("oldRecord") Map<String, Object> oldRecord);
	   
	   // 增加数据记录
	   public int insertRecord(@Param("database") String database, @Param("table") String table, @Param("newRecord") Map<String, Object> newRecord);
	   
	   // 查询分页数据记录集
	   public List<Map<String, Object>> selectRecords(@Param("database") String database, @Param("table") String table, @Param("offset") Integer offset, @Param("orderColumn") String orderColumn, @Param("orderType") String orderType, @Param("span") Integer span);
	   
	   // 查询所有数据记录集的数量
	   public Integer selectAllRecords(@Param("database") String database, @Param("table") String table);
	   
	   // 根据字段查询记录集
	   public List<Map<String, Object>> selectRecordsByColumn(@Param("database") String database, @Param("table") String table, @Param("columnName") String columnName, @Param("value") String value, @Param("offset") Integer offset, @Param("span") Integer span);
	   
	   // 根据字段查询记录总数
	   public Integer selectAllRecordsByColumn(@Param("database") String database, @Param("table") String table, @Param("columnName") String columnName, @Param("value") String value);

}
