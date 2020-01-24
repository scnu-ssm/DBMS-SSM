package com.chenrong.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chenrong.bean.User;
import com.chenrong.dao.UserMapper;

@Service
public class UserService {
	
	@Autowired
	UserMapper userMapper;
	
	// 用户注册检测
	/**
	 * 0 表示 用户名、密码为空
	 * 1 表示 用户名冲突
	 * 2 表示 邮箱冲突
	 * 4 表示 用户名、密码带有空格
	 * 5 表示检测通过
	 * @param user
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public int checkRegister(User user) {
		String username = user.getUsername();
		String password = user.getPassword();
		String email = user.getEmail();
		
		// 账号或者密码为空
		if(StringUtils.isBlank(password) || StringUtils.isBlank(username)) {
			return 0;
		}
		
		int num1 = userMapper.checkUsername(username);
		int num2 = userMapper.checkEmail(email);
		
		if(num1 >= 1)
		return 1;
		if(num2 >= 1)
		return 2;
		
		// 判断账号或者密码是否带有空格
	    if(username.length() != StringUtils.deleteSpaces(username).length() || password.length() != StringUtils.deleteSpaces(password).length())
		return 4;
		
		// 检测通过
		return 5;
	}
	
	// 用户注册
	/**
	 *  '1' 表示注册成功， '0' 表示注册失败 
	 * @param user
	 * @return
	 */
	public boolean Register(User user) {
		int num = userMapper.insert(user);
		if(num == 1)
		return true;
		
		return false;
	}
	
	// 查找注册的账号
	public User getUserId(User user) {
		// 首先判断 user 有效
		if(user == null)
		return null;
		
		return userMapper.selectBySelective(user);
	}
	
	// 用户登录
	/**
	 * '1' 表示登录成功，'0'表示登录失败
	 * @param user
	 * @return
	 */
	public boolean Login(User user) {
		int num = userMapper.login(user);
		if(num == 1)
		return true;
		
		return false;
	}
	
	// 用户修改
	/**
	 * '1' 表示修改成功，'0'表示修改失败
	 * @param user
	 * @return
	 */
	public boolean Update(User user, String restPassword) {
		int num = userMapper.updateByPrimaryKeySelective(user.getId(), user.getPassword(), restPassword);
		
		if(num == 1)
		return true;
		
		return false;
	}
	
	// 查询用户 By Username
	public User getUserByUserName(String username) {
		return userMapper.selectByUsername(username);
    }
	
	// 查询用户 By UserId
	public User getUserByUserId(String userId) {
		return userMapper.selectByUserId(userId);   
	}

}
