package com.chenrong.util;

import java.util.UUID;

public class GenerateIDUtil {
	
	// ��ȡ32λ����ַ���
	public static String getUUID32() {
		  return UUID.randomUUID().toString().replace("-", "");
	}

}
