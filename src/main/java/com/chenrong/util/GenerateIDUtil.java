package com.chenrong.util;

import java.util.Random;
import java.util.UUID;

public class GenerateIDUtil {
	
	// ��ȡ32λ����ַ���
	public static String getUUID32() {
		  return UUID.randomUUID().toString().replace("-", "");
	}
	
	// ��ȡ6λ��֤��
	public static String getCode() {
		
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < 6; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
		
	}

}
