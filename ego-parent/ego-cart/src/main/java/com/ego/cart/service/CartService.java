package com.ego.cart.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ego.common.pojo.Result;
import com.ego.common.pojo.TbItemChild;

public interface CartService {
	/**
	 * 添加商品到购物车
	 * @param id
	 * @param num
	 */
	void addCart(long id,int num,HttpServletRequest request);
	
	/**
	 * 显示购物车信息
	 * @return
	 */
	List<TbItemChild> showCart(HttpServletRequest request);
	
	/**
	 * 删除购物车中的商品，根据id删除redis中的list集合中的对应商品
	 * @param id
	 * @param request
	 * @return 返回状态
	 */
	Result delCart(long id,HttpServletRequest request);
	
	/**
	 * 更新购物车中的商品数量
	 * @param id
	 * @param num
	 * @param request
	 * @return
	 */
	Result updateCart(long id ,int num ,HttpServletRequest request);
}
