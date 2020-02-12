package com.chenrong.util;

import java.io.File;
import java.util.List;

/**
 *   ɾ����ʱ�ļ����߳�
 * @author chenrong
 *
 */
public class DeleteTask  implements Runnable{
	
	// �ļ���·��
	private String path;
	
	private List<String> paths;
	
	public DeleteTask(String path) {
		  this.path = path;
	}
	public DeleteTask(List<String> paths) {
		  this.paths = paths;
	}

	@Override
	public void run() {
		if(path != null) {
		    File file = new File(path);
		    String fileName = file.getName();
		    // ǿ��ɾ���ļ���������10������
		    for(int i = 0; i < 10 && (!file.delete()); i++) {
			   System.out.println("�� " + (i + 1) + "��ɾ����ʱ�ļ� " + fileName + " ʧ��");
			   System.gc();
		    }
		    if(!file.exists()) {
		    	System.out.println("ɾ����ʱ�ļ� " + fileName + " �ɹ�");
		    }
		}
		
		if(paths != null && paths.size() != 0) {
			 // �����ļ�ɾ��
			 for(String dest : paths) {
				    File file = new File(dest);
				    String fileName = file.getName();
				    // ǿ��ɾ���ļ���������10������
				    for(int i = 0; i < 10 && (!file.delete()); i++) {
					   System.out.println("�� " + (i + 1) + "��ɾ����ʱ�ļ� " + fileName + " ʧ��");
					   System.gc();
				    }
				    if(!file.exists()) {
				    	System.out.println("ɾ����ʱ�ļ� " + fileName + " �ɹ�");
				    }
			 }
		}
	}

}
