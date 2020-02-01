package com.scnu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zichang.bean.DBuser;
import com.zichang.bean.Privilege;

public interface DBuserMapper {
	
	//查询mysql.user表的表头
	List<String> showUserCol();
	
	//刷新权限
	int flushPrivs();
	
	//创建用户
	int createUser(@Param("username")String username, @Param("host")String host, @Param("pwd")String pwd);
	
	//删除用户
	int dropUser(@Param("username")String username, @Param("host")String host);
	
	//查询所有用户
	List<DBuser> showUsers();
	
	//根据username@host查询用户
	DBuser selectUserByName(@Param("username")String username, @Param("host")String host);
	
	//查询权限
	List<String> showPrivs(@Param("username")String username, @Param("host")String host);
	
	//修改密码
	int updatePwd(@Param("username")String username, @Param("host")String host, @Param("pwd")String pwd);
	
	//授权
	int grant(@Param("privilege")Privilege privilege);
	
	//回收权限
	int revoke(@Param("privilege")Privilege privilege);
	
}
