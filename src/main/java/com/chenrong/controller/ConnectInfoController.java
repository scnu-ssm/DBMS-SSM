package com.chenrong.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chenrong.bean.ConnectInfo;
import com.chenrong.bean.Msg;
import com.chenrong.service.ConnectInfoService;

@Controller
@RequestMapping("/connectInfo")
public class ConnectInfoController {
	
	@Autowired
	ConnectInfoService connectInfoService;
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	// 新建连接信息
	public Msg createConnect(@Param("connectInfo") ConnectInfo connectInfo) {
		
		System.out.println("我进入新建连接方法块了");
		
		boolean taf = false;
		
		taf = connectInfoService.insert(connectInfo);
		
		// 新建连接信息成功
		if(taf)
		return Msg.connectInfoInsertSuccess();
		
		// 新建连接失败
		return Msg.connectInfoInsertFailure();
	}
	
	@RequestMapping(value = "/select", method = RequestMethod.POST)
	@ResponseBody
	// 查找连接信息
	// 主要通过 connect_Name 查找记录
	public Msg selectConnect(@Param("connectInfo") ConnectInfo connectInfo) {
	
		String conenctName = connectInfo.getConnectName();
		
		// 判断空的情况
		if(StringUtils.isEmpty(conenctName))
		return null;
		
		// 获取连接信息的内容
		ConnectInfo selectConnectInfo = connectInfoService.select(conenctName);
		
		// 查找连接信息失败
		if(selectConnectInfo == null)
		return Msg.connectInfoSelectFailure();
		
		System.out.println(selectConnectInfo);
		
		// 查找连接信息成功
		return Msg.connectInfoSelectSuccess();
		
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	// 更新连接信息
	// 一定要传connect_Id过来，前台需要处理好
	public Msg updateConnect(@Param("connectInfo") ConnectInfo connectInfo) {
		
		// connect_Id 、 connect_Name的检测
		if(connectInfo.getConnectId() == 0 || connectInfo.getConnectName() == null)
		return null;
		
		boolean taf = false;
		
		taf = connectInfoService.update(connectInfo);
		
		// 更新连接信息成功
		if(taf)
		return Msg.connectInfoUpdateSuccess();
		
		// 更新连接信息失败, 连接名冲突
	    return Msg.connectInfoUpdateFailure();
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	// 主要通过 connect_Name 删除记录
	public Msg deleteConnect(@Param("connectInfo") ConnectInfo connectInfo) {
		String connectName = connectInfo.getConnectName();
		
		boolean taf = false;
		
		taf = connectInfoService.delete(connectName);
		
		// 删除连接成功
		if(taf)
		return Msg.connectInfoDeleteSuccess();
		
		// 删除连接失败
		return Msg.connectInfoDeleteFailure();
	}

}
