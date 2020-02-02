package com.chenrong.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chenrong.bean.Demo;
import com.chenrong.bean.ScnuResult;
import com.chenrong.service.DemoService;

@RequestMapping("/demo")
@Controller
public class DemoController {
	
	@Autowired
	private DemoService demoService;
	
	// 增加demo用户
	@RequestMapping("/insert")
	@ResponseBody
	public ScnuResult insert(Demo demo, String connectId, String database){
		   
		   try {
			  if(demoService.insert(demo, connectId, database) >= 1) {
		          return ScnuResult.build("新建用户成功");
			  }
		   }catch(Exception e) {
			   e.printStackTrace();
		   }
		   return ScnuResult.build("新建用户失败");
		   
	}
	
	// 查找用户
	@RequestMapping("selectById")
	@ResponseBody
    public ScnuResult selectById(String id, String connectId, String database) throws Exception{
    	  try {
    		  List<Map> demo = demoService.selectById(id, connectId, database);
    		  return ScnuResult.build(demo);
    	  }catch(Exception e) {
    		  e.printStackTrace();
    	  }
    	  return ScnuResult.build("用户不存在");
    }
}
