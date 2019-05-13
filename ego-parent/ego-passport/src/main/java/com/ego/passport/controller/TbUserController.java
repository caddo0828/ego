package com.ego.passport.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ego.common.pojo.Result;
import com.ego.passport.service.TbUserService;
import com.ego.pojo.TbUser;

@Controller
public class TbUserController {
	@Resource
	private TbUserService tbUserServiceImpl;
	
	/**
	 * 从其他界面跳转到登录界面，将跳转的路径保存在作用域中
	 * @RequestHeader 代表获取请求体中数据的参数信息,获取请求是从哪个地方发出的
	 * 设置一个默认的url，避免其他项目通过HttpClient请求时Refer请求头为空，报空指针异常
	 * @return
	 */
	@RequestMapping("user/showLogin")
	public String showLoginPage(@RequestHeader(value="Referer",defaultValue="") String url,Model model,String interUrl) {
		if(interUrl!=null&&!interUrl.equals("")) {
			model.addAttribute("redirect", interUrl);
		}else if(!url.equals("")&&url!=null){
			model.addAttribute("redirect", url);			
		}
		return "login";
	}
	
	/**
	 * 用户登录，将用户信息存储在redis中，将redis键存在cookie中
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("user/login")
	@ResponseBody
	public Result login(TbUser user,HttpServletRequest request,HttpServletResponse response) {
		return tbUserServiceImpl.login(user,request,response);
	}
	
	/**获取用户的登录信息
	 * ajax跨域请求，使用的是jsonp
	 * 因为有可能使用callback回调函数，也可能使用匿名函数，因此返回值有两种情况
	 * MappingJacksonValue 或者匿名函数中的Result对象
	 * @return
	 */
	@RequestMapping("/user/token/{token}")
	@ResponseBody
	public Object userInfo(@PathVariable String token,String callback) {
		Result result = tbUserServiceImpl.getUserInfo(token);
		//回调函数可选，浏览器存在默认的callbcak
		if(callback!=null&&!callback.equals("")) {
			MappingJacksonValue mjv = new MappingJacksonValue(result);
			mjv.setJsonpFunction(callback);
			return mjv;
		}
		return result;
	}
	
	/**
	 * 用户退出，清除redis中用户数据,并且清除对应的cookie
	 * @param token
	 * @param callback
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/user/logout/{token}")
	@ResponseBody
	public Object logout(@PathVariable String token, String callback,HttpServletRequest request ,HttpServletResponse response){
		Result result = tbUserServiceImpl.logout(token, request, response);
		//callback可选参数
		if(callback!=null&&!callback.equals("")) {
			//返回类型为      函数名(参数值)
			MappingJacksonValue mjv = new MappingJacksonValue(tbUserServiceImpl.logout(token, request, response));
			mjv.setJsonpFunction(callback);
			return mjv;
		}
		return result;
	}
	
	/**
	 * 显示用户注册界面
	 * @return
	 */
	@RequestMapping("/user/showRegister")
	public String showRegister() {
		return  "register";
	}
	
	/**
	 * 校验用户的用户名或者手机号是否已经存在
	 * @return
	 */
	@RequestMapping("/user/check/{data}/{num}")
	@ResponseBody
	public Result regCheckName(@PathVariable String data,@PathVariable int num) {
		if(num==1) {
			//代表校验的是用户名
			return tbUserServiceImpl.checkName(data);
		}else if(num==2){
			//代表校验的是手机号
			return tbUserServiceImpl.checkPhone(data);
		}
		return null;
	}
	
	/**
	 * 用户注册
	 * @param tbUser
	 * @return
	 */
	@RequestMapping("/user/register")
	@ResponseBody
	public Result register(TbUser tbUser) {
		return  tbUserServiceImpl.insUser(tbUser);
	}
}
