package com.scnu.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *  ��ʱ����ÿ��10����ִ��һ�Σ�������ڵ�����
 * @author chenrong
 *
 */

@Component
@EnableScheduling
public class ScheduledUtil {
	
	@Autowired
	ConnectManager connectManager;
	
	// ÿ10����ִ��һ��
	@Scheduled(cron = "0 0/10 * * * *")
	public void RemoveKey() {
		
		Map<String, Long> expiresPool = connectManager.geExpiresPoolMap();
		// �����Ѿ�ʧЧ��key������ɾ��
		for(String connectId : expiresPool.keySet()) {
			if(System.currentTimeMillis() >= expiresPool.get(connectId)) {
				connectManager.deleteConnect(connectId);
				expiresPool.remove(connectId);
				System.out.println("���ӳ�ʱ���Ѿ����Զ�ɾ���� connectId = " + connectId);
			}
		}
		
	}

}
