package com.ego.passport.service.impl;

import java.util.Date;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.common.pojo.Result;
import com.ego.common.utils.CookieUtils;
import com.ego.common.utils.JsonUtils;
import com.ego.dubbo.service.TbUserDubboService;
import com.ego.passport.service.TbUserService;
import com.ego.pojo.TbUser;
import com.ego.redis.dao.JedisDao;

@Service
public class TbUserServiceImpl implements TbUserService{
	@Reference
	private TbUserDubboService tbUserDubboServiceImpl;
	@Resource
	private JedisDao jedisDaoImpl;
	
	
	@Override
	public Result login(TbUser user,HttpServletRequest request,HttpServletResponse response) {
		Result result = new Result();
		TbUser tbUser = tbUserDubboServiceImpl.selUser(user);
		if(tbUser!=null) {
			result.setStatus(200);
			//随机产生redis的key
			//当用户登录成功以后将用户信息存储到redis中
			String key = UUID.randomUUID().toString();
			jedisDaoImpl.set(key, JsonUtils.objectToJson(tbUser));
			//redis设置七天登录信息有效
			jedisDaoImpl.expire(key, 60*60*24*7);
			
			//产生cookie,设置cookie的键名。键值，有效时间(和redis有效时间一致)
			CookieUtils.setCookie(request, response, "TT_TOKEN", key ,60*60*24*7 );
			
		}else {
			result.setMsg("用户名或密码错误！");
		}
		return result;
	}


	@Override
	public Result getUserInfo(String token) {
		Result result = new Result();
		String value = jedisDaoImpl.get(token);
		//双重验证：redis中键必须存在，二是值不能为空
		if(value!=null&&!value.equals("")) {
			//代表将用户数据存储了
			TbUser user = JsonUtils.jsonToPojo(value, TbUser.class);
			//将用户的密码清空，保证安全性
			user.setPassword(null);
			result.setStatus(200);
			result.setMsg("OK");
			result.setData(user);
		}
		return result;
	}


	@Override
	public Result logout(String token,HttpServletRequest request ,HttpServletResponse response) {
		Result result = new Result();
		//清除cookie数据
		CookieUtils.deleteCookie(request, response, token);
		//清除对应的redis缓存数据
		jedisDaoImpl.del(token);
		result.setStatus(200);
		result.setMsg("OK");
		result.setData("");

		return result;
	}


	@Override
	public Result checkName(String username) {
		Result result = new Result();
		TbUser tbUser = tbUserDubboServiceImpl.selByName(username);
		if(tbUser==null) {
			//表示用户名不存在
			result.setData(true);
		}else {
			result.setData(false);
		}
		return result;
	}


	@Override
	public Result checkPhone(String phone) {
		Result result = new Result();
		TbUser tbUser = tbUserDubboServiceImpl.selByPhone(phone);
		if(tbUser==null) {
			//注册手机号不存在
			result.setData(true);
		}else {
			result.setData(false);
		}
		return result;
	}


	@Override
	public Result insUser(TbUser tbUser) {
		Result result = new Result();
		Date date = new Date();
		tbUser.setCreated(date);
		tbUser.setUpdated(date);
		try {
			int index = tbUserDubboServiceImpl.insUser(tbUser);
			if(index==1) {
				//代表新增成功
				result.setStatus(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
