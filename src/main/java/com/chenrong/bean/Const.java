package com.chenrong.bean;

/**
 *   常量
 * @author chenrong
 *
 */
public class Const {
	
	public static final Integer SUCCESS = 200;
	
	public static final Integer FAILURE = 400;
	
	public static final String REGISTER_SUCCESS = "注册成功!";
	
	public static final String REGISTER_FAILURE_USERNAME = "注册失败，用户名冲突!";
	
	public static final String REGISTER_FAILURE_EMAIL = "注册失败，邮箱冲突!";
	
	public static final String REGISTER_FAILURE_TELEPHONE = "注册失败，手机号码冲突!";
	
	public static final String REGISTER_FAILURE_NULL = "注册失败，密码不能为空!";
	
	public static final String REGISTER_FAILURE_SPACE = "注册失败，账号或者密码不能带有空格!";
	
	public static final String LOGIN_SUCCESS = "登录成功!";
	
	public static final String LOGIN_FAILURE = "登录失败，账号或者密码错误!";
	
	public static final String UPDATE_SUCCESS = "信息更新成功!";
	
	public static final String UPDATE_FAILURE = "信息更新失败!";
	
	public static final String CONNECTINFO_NAME_EXISTED = "操作失败!";
	
	public static final String CONNECTINFO_INSERT_SUCCESS = "新建连接成功!";
	
	public static final String CONNECTINFO_SELECTED_SUCCESS = "查询连接成功!";
	
	public static final String CONNECTINFO_SELECTED_FAILURE = "查询连接失败，连接不存在!";
	
	public static final String CONNECTINFO_UPDATE_SUCCESS = "更新连接信息成功!";
	
	public static final String CONNECTINFO_DELETE_SUCCESS = "删除连接信息成功!";
	
	public static final String CONNECTINFO_DELETE_FAILURE = "删除连接信息失败!";
	
	// Derby的表名
	public static final String USER_TABLE_NAME = "SCNU_User";
	
	public static final String CONNECTINFO_TABLE_NAME = "SCNU_ConnectInfo";
	
	// Cookie的 key值
	public static final String userCookieKey = "User";
	
	public static final String anonymousCookieKey = "Anonymous";
	
	// Cookie的domain、path
	public static final String cookieDomain = "localhost";  // Cookie的域
	//public static final String cookieDomain = "www.chenrong.xyz";  // Cookie的域
	
	public static final String cookiePath = "/";  // Cookie的路径
	
	// Session的Key值
	public static final String USERID = "userId";
	
	public static final String VALIDATE_USERNAME = "validateUsername";
	
	public static final String VALIDATA_CODE = "validateCode";
	
	// 邮件服务常量
	public static final String SUBJECT = "密码找回 - 验证码";
	
	// 页数据的跨度
	public static final Integer SPAN = 500;

}
