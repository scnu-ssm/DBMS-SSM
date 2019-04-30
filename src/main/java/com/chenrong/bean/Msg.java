package com.chenrong.bean;

/**
 *   服务器响应给前端的消息
 * @author chenrong
 *
 */
public class Msg {
	
	// 成功 or 失败
	private boolean bool;
	
	// 状态, 提示信息
    private String status;
    
    // 注册时用户名冲突
    public static Msg registerFailureUsername() {
    	Msg msg = new Msg();
    	msg.bool = Const.FAILURE;
    	msg.status = Const.REGISTER_FAILURE_USERNAME;
    	
    	return msg;
    	
    }
    
    // 注册时邮箱冲突
    public static Msg registerFailureEmail() {
    	Msg msg = new Msg();
    	msg.bool = Const.FAILURE;
    	msg.status = Const.REGISTER_FAILURE_EMAIL;
    	
    	return msg;
    	
    }
    
    // 注册时电话冲突
    public static Msg registerFailureTelephone() {
    	Msg msg = new Msg();
    	msg.bool = Const.FAILURE;
    	msg.status = Const.REGISTER_FAILURE_TELEPHONE;
    	
    	return msg;
    	
    }
    
    // 注册时账号密码为空
    public static Msg registerFailureNull() {
    	Msg msg = new Msg();
    	msg.bool = Const.FAILURE;
    	msg.status = Const.REGISTER_FAILURE_NULL;
    	
    	return msg;
    	
    }
    
    //注册时账号密码存在空格
    public static Msg registerFailureSpace() {
    	Msg msg = new Msg();
    	msg.bool = Const.FAILURE;
    	msg.status = Const.REGISTER_FAILURE_SPACE;
    	
    	return msg;
    	
    }
    
   // 注册成功
 	public static Msg registerSuccess() {
 		Msg msg = new Msg();
 		msg.bool = Const.SUCCESS;
 		msg.status = Const.REGISTER_SUCCESS;
 		return msg;
 	}
    
 	// 登录失败
 	public static Msg loginFailure() {
 		Msg msg = new Msg();
 		msg.bool = Const.FAILURE;
 		msg.status = Const.LOGIN_FAILURE;
 		return msg;
 	}

 	// 登录成功
 	public static Msg loginSuccess() {
 	    Msg msg = new Msg();
 	    msg.bool = Const.SUCCESS;
 	    msg.status = Const.LOGIN_SUCCESS;
 	    return msg;
 	}
 	
 	// 信息更新成功
 	public static Msg updateSuccess() {
 		Msg msg = new Msg();
 		msg.bool = Const.SUCCESS;
 		msg.status = Const.UPDATE_SUCCESS;
 		return msg;
 	}
 	
 	// 信息更新失败
 	public static Msg updateFailure() {
 		Msg msg = new Msg();
 		msg.bool = Const.FAILURE;
 		msg.status = Const.UPDATE_FAILURE;
 		return msg;
 	}
 	
 	// 新建连接信息成功
 	public static Msg connectInfoInsertSuccess() {
 		Msg msg = new Msg();
 		msg.bool = Const.SUCCESS;
 		msg.status = Const.CONNECTINFO_INSERT_SUCCESS;
 		return msg;
 	}
 	
 	// 新建连接信息失败，连接名已存在
 	public static Msg connectInfoInsertFailure() {
 		Msg msg = new Msg();
 		msg.bool = Const.FAILURE;
 		msg.status = Const.CONNECTINFO_NAME_EXISTED;
 		return msg;
 	}
 	
 	// 查询连接信息成功
 	public static Msg connectInfoSelectSuccess() {
 		Msg msg = new Msg();
 		msg.bool = Const.SUCCESS;
 		msg.status = Const.CONNECTINFO_SELECTED_SUCCESS;
 		return msg;
 	}
 	
    // 查询连接信息失败
 	public static Msg connectInfoSelectFailure() {
 		Msg msg = new Msg();
 		msg.bool = Const.FAILURE;
 		msg.status = Const.CONNECTINFO_SELECTED_FAILURE;
 		return msg;
 	}
 	
 	// 更新连接信息成功
 	public static Msg connectInfoUpdateSuccess() {
 		Msg msg = new Msg();
 		msg.bool = Const.SUCCESS;
 		msg.status = Const.CONNECTINFO_UPDATE_SUCCESS;
 		return msg;
 	}
 	
 	// 更新连接信息失败，连接名已存在
 	public static Msg connectInfoUpdateFailure() {
 		Msg msg = new Msg();
 		msg.bool = Const.SUCCESS;
 		msg.status = Const.CONNECTINFO_NAME_EXISTED;
 		return msg;
 	}
 	
 	// 删除连接成功
 	public static Msg connectInfoDeleteSuccess() {
 		Msg msg = new Msg();
 		msg.bool = Const.SUCCESS;
 		msg.status = Const.CONNECTINFO_DELETE_SUCCESS;
 		return msg;
 	}
 	
 	// 删除连接失败
 	public static Msg connectInfoDeleteFailure() {
 		Msg msg = new Msg();
 		msg.bool = Const.SUCCESS;
 		msg.status = Const.CONNECTINFO_DELETE_FAILURE;
 		return msg;
 	}
 	
	public boolean isBool() {
		return bool;
	}

	public void setBool(boolean bool) {
		this.bool = bool;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}   

}
