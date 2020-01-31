package com.chenrong.bean;

/**
 *   服务器响应给前端的消息
 * @author chenrong
 *
 */
public class ScnuResult {
	
	// 成功 or 失败
	private Integer code;
	
	// 状态, 提示信息
    private String status;
    
    // 返回数据
    private Object data; 
    
    public Object getData() {
		return data;
	}

	public void setData(Object object) {
		this.data = object;
	}

	// 注册时用户名冲突
    public static ScnuResult registerFailureUsername() {
    	ScnuResult msg = new ScnuResult();
    	msg.code = Const.FAILURE;
    	msg.status = Const.REGISTER_FAILURE_USERNAME;
    	
    	return msg;
    	
    }
    
    // 注册时邮箱冲突
    public static ScnuResult registerFailureEmail() {
    	ScnuResult msg = new ScnuResult();
    	msg.code = Const.FAILURE;
    	msg.status = Const.REGISTER_FAILURE_EMAIL;
    	
    	return msg;
    	
    }
    
    
    // 注册时账号密码为空
    public static ScnuResult registerFailureNull() {
    	ScnuResult msg = new ScnuResult();
    	msg.code = Const.FAILURE;
    	msg.status = Const.REGISTER_FAILURE_NULL;
    	
    	return msg;
    	
    }
    
    //注册时账号密码存在空格
    public static ScnuResult registerFailureSpace() {
    	ScnuResult msg = new ScnuResult();
    	msg.code = Const.FAILURE;
    	msg.status = Const.REGISTER_FAILURE_SPACE;
    	
    	return msg;
    	
    }
    
   // 注册成功
 	public static ScnuResult registerSuccess() {
 		ScnuResult msg = new ScnuResult();
 		msg.code = Const.SUCCESS;
 		msg.status = Const.REGISTER_SUCCESS;
 		return msg;
 	}
    
 	// 登录失败
 	public static ScnuResult loginFailure() {
 		ScnuResult msg = new ScnuResult();
 		msg.code = Const.FAILURE;
 		msg.status = Const.LOGIN_FAILURE;
 		return msg;
 	}

 	// 登录成功
 	public static ScnuResult loginSuccess() {
 	    ScnuResult msg = new ScnuResult();
 	    msg.code = Const.SUCCESS;
 	    msg.status = Const.LOGIN_SUCCESS;
 	    return msg;
 	}
 	
 	// 信息更新成功
 	public static ScnuResult updateSuccess() {
 		ScnuResult msg = new ScnuResult();
 		msg.code = Const.SUCCESS;
 		msg.status = Const.UPDATE_SUCCESS;
 		return msg;
 	}
 	
 	// 信息更新失败
 	public static ScnuResult updateFailure() {
 		ScnuResult msg = new ScnuResult();
 		msg.code = Const.FAILURE;
 		msg.status = Const.UPDATE_FAILURE;
 		return msg;
 	}
 	
 	// 新建连接信息成功
 	public static ScnuResult connectInfoInsertSuccess() {
 		ScnuResult msg = new ScnuResult();
 		msg.code = Const.SUCCESS;
 		msg.status = Const.CONNECTINFO_INSERT_SUCCESS;
 		return msg;
 	}
 	
 	// 新建连接信息失败，连接名已存在
 	public static ScnuResult connectInfoInsertFailure() {
 		ScnuResult msg = new ScnuResult();
 		msg.code = Const.FAILURE;
 		msg.status = Const.CONNECTINFO_NAME_EXISTED;
 		return msg;
 	}
 	
 	// 查询连接信息成功
 	public static ScnuResult connectInfoSelectSuccess() {
 		ScnuResult msg = new ScnuResult();
 		msg.code = Const.SUCCESS;
 		msg.status = Const.CONNECTINFO_SELECTED_SUCCESS;
 		return msg;
 	}
 	
    // 查询连接信息失败
 	public static ScnuResult connectInfoSelectFailure() {
 		ScnuResult msg = new ScnuResult();
 		msg.code = Const.FAILURE;
 		msg.status = Const.CONNECTINFO_SELECTED_FAILURE;
 		return msg;
 	}
 	
 	// 更新连接信息成功
 	public static ScnuResult connectInfoUpdateSuccess() {
 		ScnuResult msg = new ScnuResult();
 		msg.code = Const.SUCCESS;
 		msg.status = Const.CONNECTINFO_UPDATE_SUCCESS;
 		return msg;
 	}
 	
 	// 更新连接信息失败，连接名已存在
 	public static ScnuResult connectInfoUpdateFailure() {
 		ScnuResult msg = new ScnuResult();
 		msg.code = Const.SUCCESS;
 		msg.status = Const.CONNECTINFO_NAME_EXISTED;
 		return msg;
 	}
 	
 	// 删除连接成功
 	public static ScnuResult connectInfoDeleteSuccess() {
 		ScnuResult msg = new ScnuResult();
 		msg.code = Const.SUCCESS;
 		msg.status = Const.CONNECTINFO_DELETE_SUCCESS;
 		return msg;
 	}
 	
 	// 删除连接失败
 	public static ScnuResult connectInfoDeleteFailure() {
 		ScnuResult msg = new ScnuResult();
 		msg.code = Const.SUCCESS;
 		msg.status = Const.CONNECTINFO_DELETE_FAILURE;
 		return msg;
 	}
 	
 	//创建响应体
    public static ScnuResult build(Object oj) {
    	ScnuResult msg = new ScnuResult();
    	msg.code = Const.SUCCESS;
    	msg.status = "成功";
    	msg.setData(oj);
    	
    	return msg;
    	
    }
    
    //操作失败，创建响应体
    public static ScnuResult buildFalure(Object oj) {
    	ScnuResult msg = new ScnuResult();
    	msg.code = Const.FAILURE;
    	msg.status = "失败";
    	msg.setData(oj);
    	
    	return msg;
    }
    
	//违法操作
    public static ScnuResult forbidden(Object oj) {
    	ScnuResult msg = new ScnuResult();
    	msg.code = Const.FAILURE;
    	msg.status = "失败";
    	msg.setData(oj);
    	
    	return msg;
    	
    }

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}   

}
