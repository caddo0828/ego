package com.ego.dubbo.service;

import com.ego.pojo.TbUser;

public interface TbUserDubboService {
	/**
	 * 通过传递的user对象查询数据库中的对象信息(登录查询)
	 * @param user
	 * @return
	 */
	TbUser selUser(TbUser user);
	
	/**
	 * 通过用户名查找对应用户
	 * @param username
	 * @return
	 */
	TbUser selByName(String username);
	
	/**
	 * 通过用户注册手机号查询用户对象
	 * @param phone
	 * @return
	 */
	TbUser selByPhone(String phone);
	
	/**
	 * 插入用户
	 * @param tbUser
	 * @return
	 */
	int insUser(TbUser tbUser)throws Exception;
}
