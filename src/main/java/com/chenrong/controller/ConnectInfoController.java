package com.chenrong.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chenrong.bean.ConnectInfo;
import com.chenrong.bean.ScnuResult;
import com.chenrong.service.ConnectInfoService;
import com.chenrong.util.CookieUtil;
import com.chenrong.util.GenerateIDUtil;

@Controller
@RequestMapping("/connectInfo")
public class ConnectInfoController {
	
	@Autowired
	ConnectInfoService connectInfoService;
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	// 新建连接信息
	public ScnuResult createConnect(@Param("connectInfo") ConnectInfo connectInfo, HttpServletRequest request) {
		
		System.out.println("新建连接");
		String userId = CookieUtil.getUserID(request);
		boolean taf = false;
		connectInfo.setConnectId(GenerateIDUtil.getUUID32());
		connectInfo.setUserId(userId);
		System.out.println("connectInfo  :  " + connectInfo.toString());
		taf = connectInfoService.insert(connectInfo);
		
		// 新建连接信息成功
		if(taf) {
		   return ScnuResult.connectInfoInsertSuccess();
		}
		// 新建连接失败
		return ScnuResult.connectInfoInsertFailure();
	}
	
	@RequestMapping(value = "/selectByConnectId", method = RequestMethod.GET)
	@ResponseBody
	// 查找连接信息
	// 主要通过 conenctId 查找记录
	public ScnuResult selectConnect(String connectId) {
		
		// 判断空的情况
		if(StringUtils.isEmpty(connectId)) {
		   return null;
		}
		
		// 获取连接信息的内容
		ConnectInfo selectConnectInfo = connectInfoService.selectByConnectId(connectId);
		
		// 查找连接信息失败
		if(selectConnectInfo == null) {
		   return ScnuResult.connectInfoSelectFailure();
		}
		System.out.println(selectConnectInfo.toString());
		
		// 将UserId的信息置空
		selectConnectInfo.setUserId(null);
		
		// 查找连接信息成功
		return ScnuResult.build(selectConnectInfo);
		
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	// 更新连接信息
	// 一定要传connect_Id过来，前台需要处理好
	public ScnuResult updateConnect(@Param("connectInfo") ConnectInfo connectInfo) {
		
		// connect_Id检测
		if(connectInfo.getConnectId() == null) {
		   return null;
		}
		boolean taf = false;
		taf = connectInfoService.update(connectInfo);
		// 更新连接信息成功
		if(taf) {
		    return ScnuResult.connectInfoUpdateSuccess();
		}
		
		// 更新连接信息失败
	    return ScnuResult.connectInfoUpdateFailure();
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	// 主要通过 connect_Id 删除记录
	public ScnuResult deleteConnect(String connectId) {
		
		boolean taf = false;
		taf = connectInfoService.delete(connectId);
		// 删除连接成功
		if(taf) {
		    return ScnuResult.connectInfoDeleteSuccess();
		}
		// 删除连接失败
		return ScnuResult.connectInfoDeleteFailure();
		
	}
	
	// 通过UserId查询连接
	@RequestMapping(value = "/selectByUserId", method = RequestMethod.GET)
	@ResponseBody
	public ScnuResult selectConnectByUserId(HttpServletRequest request) {
		String userId = CookieUtil.getUserID(request);
		List<ConnectInfo> list = connectInfoService.selectByUserId(userId);
		// 将UserId的信息置空
		for(ConnectInfo connect : list) {
			connect.setUserId(null);
		}
		return ScnuResult.build(list);
	}

}
