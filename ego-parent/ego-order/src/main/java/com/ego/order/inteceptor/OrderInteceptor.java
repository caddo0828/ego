package com.ego.order.inteceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ego.common.pojo.Result;
import com.ego.common.utils.CookieUtils;
import com.ego.common.utils.HttpClientUtil;
import com.ego.common.utils.JsonUtils;

public class OrderInteceptor implements HandlerInterceptor{

	//执行控制器之前执行，根据返回的结果判断是否可以执行控制器
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//判断用户是否已经登录，从cookie中获取键值
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		if(token!=null&&!token.equals("")) {
			//响应体,是一个有Result对象转换过来的字符串
			String entity = HttpClientUtil.doPost("http://localhost:8084/user/token/"+token);
			Result result = JsonUtils.jsonToPojo(entity, Result.class);
			if(result.getStatus()==200) {
				//表示获取用户信息成功，代表用户登录了，并且存储了用户信息在redis中
				return true;
			}
		}
		
		response.sendRedirect("http://localhost:8084/user/showLogin?interUrl="+request.getRequestURL());
		return false;
	}

	//控制器执行完成后，进入jsp页面之前执行
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	//jsp执行以后执行，中途不管有没有发生异常都会执行
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
