package com.chenrong.util;

import java.util.UUID;

public class GenerateIDUtil {
	
	// 获取32位随机字符串
	public static String getUUID32() {
		  return UUID.randomUUID().toString().replace("-", "");
	}

}
