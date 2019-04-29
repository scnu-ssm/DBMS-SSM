package com.chenrong.bean;

/**
 *   ��������Ӧ��ǰ�˵���Ϣ
 * @author chenrong
 *
 */
public class Msg {
	
	// �ɹ� or ʧ��
	private boolean bool;
	
	// ״̬, ��ʾ��Ϣ
    private String status;
    
    // ע��ʱ�û�����ͻ
    public static Msg registerFailureUsername() {
    	Msg msg = new Msg();
    	msg.bool = Const.FAILURE;
    	msg.status = Const.REGISTER_FAILURE_USERNAME;
    	
    	return msg;
    	
    }
    
    // ע��ʱ�����ͻ
    public static Msg registerFailureEmail() {
    	Msg msg = new Msg();
    	msg.bool = Const.FAILURE;
    	msg.status = Const.REGISTER_FAILURE_EMAIL;
    	
    	return msg;
    	
    }
    
    // ע��ʱ�绰��ͻ
    public static Msg registerFailureTelephone() {
    	Msg msg = new Msg();
    	msg.bool = Const.FAILURE;
    	msg.status = Const.REGISTER_FAILURE_TELEPHONE;
    	
    	return msg;
    	
    }
    
    // ע��ʱ�˺�����Ϊ��
    public static Msg registerFailureNull() {
    	Msg msg = new Msg();
    	msg.bool = Const.FAILURE;
    	msg.status = Const.REGISTER_FAILURE_NULL;
    	
    	return msg;
    	
    }
    
    //ע��ʱ�˺�����Ϊ��
    public static Msg registerFailureSpace() {
    	Msg msg = new Msg();
    	msg.bool = Const.FAILURE;
    	msg.status = Const.REGISTER_FAILURE_SPACE;
    	
    	return msg;
    	
    }
    
   // ע��ɹ�
 	public static Msg registerSuccess() {
 		Msg msg = new Msg();
 		msg.bool = Const.SUCCESS;
 		msg.status = Const.REGISTER_SUCCESS;
 		return msg;
 	}
    
 	// ��¼ʧ��
 	public static Msg loginFailure() {
 		Msg msg = new Msg();
 		msg.bool = Const.FAILURE;
 		msg.status = Const.LOGIN_FAILURE;
 		return msg;
 	}

 	// ��¼�ɹ�
 	public static Msg loginSuccess() {
 	    Msg msg = new Msg();
 	    msg.bool = Const.SUCCESS;
 	    msg.status = Const.LOGIN_SUCCESS;
 	    return msg;
 	}
 	
 	// ��Ϣ���³ɹ�
 	public static Msg updateSuccess() {
 		Msg msg = new Msg();
 		msg.bool = Const.SUCCESS;
 		msg.status = Const.UPDATE_SUCCESS;
 		return msg;
 	}
 	
 	// ��Ϣ����ʧ��
 	public static Msg updateFailure() {
 		Msg msg = new Msg();
 		msg.bool = Const.FAILURE;
 		msg.status = Const.UPDATE_FAILURE;
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
