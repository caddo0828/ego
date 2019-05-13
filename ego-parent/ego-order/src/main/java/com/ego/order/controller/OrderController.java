package com.ego.order.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ego.common.pojo.Result;
import com.ego.order.pojo.OrderParam;
import com.ego.order.service.OrderService;

@Controller
public class OrderController {
	@Resource
	private OrderService orderServiceImpl;
	
	@RequestMapping("order/order-cart.html")
	public String showOrder(@RequestParam(value="id") List<Long> ids,HttpServletRequest request,Model model) {
		model.addAttribute("cartList", orderServiceImpl.showOrderCart(ids, request));
		return "order-cart";
	}
	
	@RequestMapping("/order/create.html")
	public String orderCreate(OrderParam param,HttpServletRequest request) {
		Result result = orderServiceImpl.createOrder(param, request);
		if(result.getStatus()==200) {
			return "success";
		}else {
			//跳转到异常界面
			request.setAttribute("message", "商品购买失败,请重试");
			return "error/exception";
			
		}
		
	}
	
}
