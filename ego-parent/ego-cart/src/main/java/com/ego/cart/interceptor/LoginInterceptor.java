package com.ego.cart.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ego.common.pojo.Result;
import com.ego.common.utils.CookieUtils;
import com.ego.common.utils.HttpClientUtil;
import com.ego.common.utils.JsonUtils;

/**
 * 登录拦截器
 * @author 老腰
 *
 */
public class LoginInterceptor implements HandlerInterceptor{

	//进入控制器之前执行，返回值代表是否接着执行控制器
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//获取redis中的值（redis的键）
		String cookieValue = CookieUtils.getCookieValue(request, "TT_TOKEN");
		//去请求其他项目的控制器(ego-passport)，判断是否存在用户数据, 返回的是一个响应体数据
		//响应体原本返回的是一个Result对象，将其转成了字符串类型，在通过工具类转换成对象
		//只要对象中状态码为200，表明用户已经登录，缓存中已经有数据
		String entity = HttpClientUtil.doPost("http://localhost:8084/user/token/"+cookieValue);
		if(entity!=null&&!entity.equals("")){
			Result result = JsonUtils.jsonToPojo(entity, Result.class);
			if(result.getStatus()==200) {
				return true;
			}
		}
		
		//代表用户未登录，或者已经安全退出过，此时跳转到登录界面,此时浏览器中没有Refer请求头，需要设置ego-passport中Refer请求参数有一个默认值
		//获取到是哪个控制器走的该页面，登录成功后再跳到原请求界面
		//获取到添加到购物车时的请求参数数量
		String num = request.getParameter("num");
		//url地址中拼接? ,要使用转义字符%3F 
		//相当于 Http://localhost:8085/cart/add/id.html?num=1
		response.sendRedirect("http://localhost:8084/user/showLogin?interUrl="+request.getRequestURL()+"%3Fnum="+num);
		return false;
	}

	//控制器执行完成之后，执行jsp页面之前
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	//跳转jsp界面之后执行，就算抛异常也执行
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
