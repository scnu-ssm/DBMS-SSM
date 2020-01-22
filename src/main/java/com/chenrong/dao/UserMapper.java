package com.chenrong.dao;

import com.chenrong.bean.User;

public interface UserMapper {
	
	// 注册
	int insert(User user);
	
	// 通过主键搜索
	User selectByPrimaryKey(Integer id);
	
	// 通过部分字段搜索，不走索引
	User selectBySelective(User user);
	
	// 登录
	int login(User user);
	
	// 个人信息更新
	int updateByPrimaryKeySelective(User user);
	
	// 用户名检测
	int checkUsername(String username);
	
	// 邮箱检测
	int checkEmail(String email);
	
	// 查询用户 By username
	User selectByUsername(String username);
	
	// 查询用户 By userId
	User selectByUserId(String userId);

}
