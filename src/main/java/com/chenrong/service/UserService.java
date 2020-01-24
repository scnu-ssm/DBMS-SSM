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
	
	// �û�ע����
	/**
	 * 0 ��ʾ �û���������Ϊ��
	 * 1 ��ʾ �û�����ͻ
	 * 2 ��ʾ �����ͻ
	 * 4 ��ʾ �û�����������пո�
	 * 5 ��ʾ���ͨ��
	 * @param user
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public int checkRegister(User user) {
		String username = user.getUsername();
		String password = user.getPassword();
		String email = user.getEmail();
		
		// �˺Ż�������Ϊ��
		if(StringUtils.isBlank(password) || StringUtils.isBlank(username)) {
			return 0;
		}
		
		int num1 = userMapper.checkUsername(username);
		int num2 = userMapper.checkEmail(email);
		
		if(num1 >= 1)
		return 1;
		if(num2 >= 1)
		return 2;
		
		// �ж��˺Ż��������Ƿ���пո�
	    if(username.length() != StringUtils.deleteSpaces(username).length() || password.length() != StringUtils.deleteSpaces(password).length())
		return 4;
		
		// ���ͨ��
		return 5;
	}
	
	// �û�ע��
	/**
	 *  '1' ��ʾע��ɹ��� '0' ��ʾע��ʧ�� 
	 * @param user
	 * @return
	 */
	public boolean Register(User user) {
		int num = userMapper.insert(user);
		if(num == 1)
		return true;
		
		return false;
	}
	
	// ����ע����˺�
	public User getUserId(User user) {
		// �����ж� user ��Ч
		if(user == null)
		return null;
		
		return userMapper.selectBySelective(user);
	}
	
	// �û���¼
	/**
	 * '1' ��ʾ��¼�ɹ���'0'��ʾ��¼ʧ��
	 * @param user
	 * @return
	 */
	public boolean Login(User user) {
		int num = userMapper.login(user);
		if(num == 1)
		return true;
		
		return false;
	}
	
	// �û��޸�
	/**
	 * '1' ��ʾ�޸ĳɹ���'0'��ʾ�޸�ʧ��
	 * @param user
	 * @return
	 */
	public boolean Update(User user, String restPassword) {
		int num = userMapper.updateByPrimaryKeySelective(user.getId(), user.getPassword(), restPassword);
		
		if(num == 1)
		return true;
		
		return false;
	}
	
	// ��ѯ�û� By Username
	public User getUserByUserName(String username) {
		return userMapper.selectByUsername(username);
    }
	
	// ��ѯ�û� By UserId
	public User getUserByUserId(String userId) {
		return userMapper.selectByUserId(userId);   
	}

}
