package com.chenrong.bean;

/**
 *   ����
 * @author chenrong
 *
 */
public class Const {
	
	public static final Integer SUCCESS = 200;
	
	public static final Integer FAILURE = 400;
	
	public static final String REGISTER_SUCCESS = "ע��ɹ�!";
	
	public static final String REGISTER_FAILURE_USERNAME = "ע��ʧ�ܣ��û�����ͻ!";
	
	public static final String REGISTER_FAILURE_EMAIL = "ע��ʧ�ܣ������ͻ!";
	
	public static final String REGISTER_FAILURE_TELEPHONE = "ע��ʧ�ܣ��ֻ������ͻ!";
	
	public static final String REGISTER_FAILURE_NULL = "ע��ʧ�ܣ����벻��Ϊ��!";
	
	public static final String REGISTER_FAILURE_SPACE = "ע��ʧ�ܣ��˺Ż������벻�ܴ��пո�!";
	
	public static final String LOGIN_SUCCESS = "��¼�ɹ�!";
	
	public static final String LOGIN_FAILURE = "��¼ʧ�ܣ��˺Ż����������!";
	
	public static final String UPDATE_SUCCESS = "��Ϣ���³ɹ�!";
	
	public static final String UPDATE_FAILURE = "��Ϣ����ʧ��!";
	
	public static final String CONNECTINFO_NAME_EXISTED = "����ʧ��!";
	
	public static final String CONNECTINFO_INSERT_SUCCESS = "�½����ӳɹ�!";
	
	public static final String CONNECTINFO_SELECTED_SUCCESS = "��ѯ���ӳɹ�!";
	
	public static final String CONNECTINFO_SELECTED_FAILURE = "��ѯ����ʧ�ܣ����Ӳ�����!";
	
	public static final String CONNECTINFO_UPDATE_SUCCESS = "����������Ϣ�ɹ�!";
	
	public static final String CONNECTINFO_DELETE_SUCCESS = "ɾ��������Ϣ�ɹ�!";
	
	public static final String CONNECTINFO_DELETE_FAILURE = "ɾ��������Ϣʧ��!";
	
	// Derby�ı���
	public static final String USER_TABLE_NAME = "SCNU_User";
	
	public static final String CONNECTINFO_TABLE_NAME = "SCNU_ConnectInfo";
	
	// Cookie�� keyֵ
	public static final String userCookieKey = "User";
	
	public static final String anonymousCookieKey = "Anonymous";
	
	// Cookie��domain��path
	public static final String cookieDomain = "localhost";  // Cookie����
	//public static final String cookieDomain = "www.chenrong.xyz";  // Cookie����
	
	public static final String cookiePath = "/";  // Cookie��·��
	
	// Session��Keyֵ
	public static final String USERID = "userId";
	
	public static final String VALIDATE_USERNAME = "validateUsername";
	
	public static final String VALIDATA_CODE = "validateCode";
	
	// �ʼ�������
	public static final String SUBJECT = "�����һ� - ��֤��";
	
	// ҳ���ݵĿ��
	public static final Integer SPAN = 500;

}
