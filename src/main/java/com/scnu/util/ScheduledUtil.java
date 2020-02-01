package com.scnu.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *  定时任务，每隔10分钟执行一次，清除过期的连接
 * @author chenrong
 *
 */

@Component
@EnableScheduling
public class ScheduledUtil {
	
	@Autowired
	ConnectManager connectManager;
	
	// 每10分钟执行一次
	@Scheduled(cron = "0 0/10 * * * *")
	public void RemoveKey() {
		
		Map<String, Long> expiresPool = connectManager.geExpiresPoolMap();
		// 遍历已经失效的key，并且删除
		for(String connectId : expiresPool.keySet()) {
			if(System.currentTimeMillis() >= expiresPool.get(connectId)) {
				connectManager.deleteConnect(connectId);
				expiresPool.remove(connectId);
				System.out.println("连接超时，已经被自动删除， connectId = " + connectId);
			}
		}
		
	}

}
