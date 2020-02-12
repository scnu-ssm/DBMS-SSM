package com.chenrong.util;

import java.io.File;
import java.util.List;

/**
 *   删除临时文件的线程
 * @author chenrong
 *
 */
public class DeleteTask  implements Runnable{
	
	// 文件的路径
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
		    // 强制删除文件，最多进行10次重试
		    for(int i = 0; i < 10 && (!file.delete()); i++) {
			   System.out.println("第 " + (i + 1) + "次删除临时文件 " + fileName + " 失败");
			   System.gc();
		    }
		    if(!file.exists()) {
		    	System.out.println("删除临时文件 " + fileName + " 成功");
		    }
		}
		
		if(paths != null && paths.size() != 0) {
			 // 遍历文件删除
			 for(String dest : paths) {
				    File file = new File(dest);
				    String fileName = file.getName();
				    // 强制删除文件，最多进行10次重试
				    for(int i = 0; i < 10 && (!file.delete()); i++) {
					   System.out.println("第 " + (i + 1) + "次删除临时文件 " + fileName + " 失败");
					   System.gc();
				    }
				    if(!file.exists()) {
				    	System.out.println("删除临时文件 " + fileName + " 成功");
				    }
			 }
		}
	}

}
