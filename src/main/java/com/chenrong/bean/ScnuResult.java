package com.chenrong.bean;

/**
 *   ��������Ӧ��ǰ�˵���Ϣ
 * @author chenrong
 *
 */
public class ScnuResult {
	
	// �ɹ� or ʧ��
	private Integer code;
	
	// ״̬, ��ʾ��Ϣ
    private String status;
    
    // ��������
    private Object data; 
    
    public Object getData() {
		return data;
	}

	public void setData(Object object) {
		this.data = object;
	}

	// ע��ʱ�û�����ͻ
    public static ScnuResult registerFailureUsername() {
    	ScnuResult msg = new ScnuResult();
    	msg.code = Const.FAILURE;
    	msg.status = Const.REGISTER_FAILURE_USERNAME;
    	
    	return msg;
    	
    }
    
    // ע��ʱ�����ͻ
    public static ScnuResult registerFailureEmail() {
    	ScnuResult msg = new ScnuResult();
    	msg.code = Const.FAILURE;
    	msg.status = Const.REGISTER_FAILURE_EMAIL;
    	
    	return msg;
    	
    }
    
    
    // ע��ʱ�˺�����Ϊ��
    public static ScnuResult registerFailureNull() {
    	ScnuResult msg = new ScnuResult();
    	msg.code = Const.FAILURE;
    	msg.status = Const.REGISTER_FAILURE_NULL;
    	
    	return msg;
    	
    }
    
    //ע��ʱ�˺�������ڿո�
    public static ScnuResult registerFailureSpace() {
    	ScnuResult msg = new ScnuResult();
    	msg.code = Const.FAILURE;
    	msg.status = Const.REGISTER_FAILURE_SPACE;
    	
    	return msg;
    	
    }
    
   // ע��ɹ�
 	public static ScnuResult registerSuccess() {
 		ScnuResult msg = new ScnuResult();
 		msg.code = Const.SUCCESS;
 		msg.status = Const.REGISTER_SUCCESS;
 		return msg;
 	}
    
 	// ��¼ʧ��
 	public static ScnuResult loginFailure() {
 		ScnuResult msg = new ScnuResult();
 		msg.code = Const.FAILURE;
 		msg.status = Const.LOGIN_FAILURE;
 		return msg;
 	}

 	// ��¼�ɹ�
 	public static ScnuResult loginSuccess() {
 	    ScnuResult msg = new ScnuResult();
 	    msg.code = Const.SUCCESS;
 	    msg.status = Const.LOGIN_SUCCESS;
 	    return msg;
 	}
 	
 	// ��Ϣ���³ɹ�
 	public static ScnuResult updateSuccess() {
 		ScnuResult msg = new ScnuResult();
 		msg.code = Const.SUCCESS;
 		msg.status = Const.UPDATE_SUCCESS;
 		return msg;
 	}
 	
 	// ��Ϣ����ʧ��
 	public static ScnuResult updateFailure() {
 		ScnuResult msg = new ScnuResult();
 		msg.code = Const.FAILURE;
 		msg.status = Const.UPDATE_FAILURE;
 		return msg;
 	}
 	
 	// �½�������Ϣ�ɹ�
 	public static ScnuResult connectInfoInsertSuccess() {
 		ScnuResult msg = new ScnuResult();
 		msg.code = Const.SUCCESS;
 		msg.status = Const.CONNECTINFO_INSERT_SUCCESS;
 		return msg;
 	}
 	
 	// �½�������Ϣʧ�ܣ��������Ѵ���
 	public static ScnuResult connectInfoInsertFailure() {
 		ScnuResult msg = new ScnuResult();
 		msg.code = Const.FAILURE;
 		msg.status = Const.CONNECTINFO_NAME_EXISTED;
 		return msg;
 	}
 	
 	// ��ѯ������Ϣ�ɹ�
 	public static ScnuResult connectInfoSelectSuccess() {
 		ScnuResult msg = new ScnuResult();
 		msg.code = Const.SUCCESS;
 		msg.status = Const.CONNECTINFO_SELECTED_SUCCESS;
 		return msg;
 	}
 	
    // ��ѯ������Ϣʧ��
 	public static ScnuResult connectInfoSelectFailure() {
 		ScnuResult msg = new ScnuResult();
 		msg.code = Const.FAILURE;
 		msg.status = Const.CONNECTINFO_SELECTED_FAILURE;
 		return msg;
 	}
 	
 	// ����������Ϣ�ɹ�
 	public static ScnuResult connectInfoUpdateSuccess() {
 		ScnuResult msg = new ScnuResult();
 		msg.code = Const.SUCCESS;
 		msg.status = Const.CONNECTINFO_UPDATE_SUCCESS;
 		return msg;
 	}
 	
 	// ����������Ϣʧ�ܣ��������Ѵ���
 	public static ScnuResult connectInfoUpdateFailure() {
 		ScnuResult msg = new ScnuResult();
 		msg.code = Const.SUCCESS;
 		msg.status = Const.CONNECTINFO_NAME_EXISTED;
 		return msg;
 	}
 	
 	// ɾ�����ӳɹ�
 	public static ScnuResult connectInfoDeleteSuccess() {
 		ScnuResult msg = new ScnuResult();
 		msg.code = Const.SUCCESS;
 		msg.status = Const.CONNECTINFO_DELETE_SUCCESS;
 		return msg;
 	}
 	
 	// ɾ������ʧ��
 	public static ScnuResult connectInfoDeleteFailure() {
 		ScnuResult msg = new ScnuResult();
 		msg.code = Const.SUCCESS;
 		msg.status = Const.CONNECTINFO_DELETE_FAILURE;
 		return msg;
 	}
 	
 	//������Ӧ��
    public static ScnuResult build(Object oj) {
    	ScnuResult msg = new ScnuResult();
    	msg.code = Const.SUCCESS;
    	msg.status = "�ɹ�";
    	msg.setData(oj);
    	
    	return msg;
    	
    }
    
    //����ʧ�ܣ�������Ӧ��
    public static ScnuResult buildFalure(Object oj) {
    	ScnuResult msg = new ScnuResult();
    	msg.code = Const.FAILURE;
    	msg.status = "ʧ��";
    	msg.setData(oj);
    	
    	return msg;
    }
    
	//Υ������
    public static ScnuResult forbidden(Object oj) {
    	ScnuResult msg = new ScnuResult();
    	msg.code = Const.FAILURE;
    	msg.status = "ʧ��";
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
