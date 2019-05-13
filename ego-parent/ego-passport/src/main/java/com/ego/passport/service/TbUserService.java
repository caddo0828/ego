package com.ego.passport.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ego.common.pojo.Result;
import com.ego.pojo.TbUser;

public interface TbUserService {
	/**
	 * 返回一个Result对象给前台，进行登录验证
	 * @param user
	 * @return
	 */
	Result login(TbUser user,HttpServletRequest request,HttpServletResponse response);
	
	/**
	 * 通过token（也就是cookie的键值，redis中存储用户信息的键值）获取用户的信息
	 * @param token
	 * @return
	 */
	Result getUserInfo(String token);
	
	/**
	 * 通过token清除redis中的用户数据，并且使cookie失效
	 * @param token
	 * @return
	 */
	Result logout(String token,HttpServletRequest request ,HttpServletResponse response);
	
	/**
	 * 验证用户名是否存在
	 * @param name
	 * @return
	 */
	Result checkName(String username);
	
	/**
	 * 验证用户注册手机是否存在
	 * @param phone
	 * @return
	 */
	Result checkPhone(String phone);
	
	/**
	 * 用户注册
	 * @param username
	 * @param password
	 * @param phone
	 * @return
	 */
	Result insUser(TbUser tbUser);
	
}
