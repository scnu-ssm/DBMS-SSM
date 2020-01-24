package com.chenrong.util;

import java.util.Random;
import java.util.UUID;

public class GenerateIDUtil {
	
	// 获取32位随机字符串
	public static String getUUID32() {
		  return UUID.randomUUID().toString().replace("-", "");
	}
	
	// 获取6位验证码
	public static String getCode() {
		
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < 6; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
		
	}

}
