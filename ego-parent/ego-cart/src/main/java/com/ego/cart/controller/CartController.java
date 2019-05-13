package com.ego.cart.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.cart.service.CartService;
import com.ego.common.pojo.Result;

@Controller
public class CartController {
	@Resource
	private CartService cartServiceImpl;
	
	/**
	 * 商品添加到购物车
	 * @param id
	 * @return
	 */
	@RequestMapping("cart/add/{id}.html")
	public String addCart(@PathVariable long id,int num,HttpServletRequest request) {
		//将商品保存到redis中
		cartServiceImpl.addCart(id, num, request);
		return "cartSuccess";
	}
	
	/**
	 * 显示购物车商品数据
	 * @return
	 */
	@RequestMapping("cart/cart.html")
	public String showCart(Model model,HttpServletRequest request) {
		/**
		 * 跳转到购物车界面时，获取redis集合中的数据
		 * 存放在作用域中，传递给前台购物车界面
		 */
		model.addAttribute("cartList",cartServiceImpl.showCart(request));
		return "cart";
	}
	
	@RequestMapping("/cart/delete/{id}.action")
	@ResponseBody
	public Result delCart(@PathVariable long id,HttpServletRequest request) {
		return cartServiceImpl.delCart(id, request);
	}
	
	@RequestMapping("/cart/update/num/{id}/{num}.action")
	@ResponseBody
	public Result updateCart(@PathVariable long id ,@PathVariable int num ,HttpServletRequest request) {
		return cartServiceImpl.updateCart(id, num, request);
	}
	
	/**
	 * 购物车界面，超链接（删除选中商品操作）
	 * @param ids
	 * @return
	 */
	@RequestMapping("/cart/deleteAll.html")
	@ResponseBody
	public Result delChecked(String ids) {
		System.out.println(ids);
		Result result = new Result();
		result.setStatus(200);
		return result;
	}

}
