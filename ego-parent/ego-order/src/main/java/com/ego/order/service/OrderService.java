package com.ego.order.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ego.common.pojo.Result;
import com.ego.common.pojo.TbItemChild;
import com.ego.order.pojo.OrderParam;

public interface OrderService {
	/**
	 * 确认订单信息，显示订单中包含的商品
	 * @param ids
	 * @param request
	 * @return
	 */
	List<TbItemChild> showOrderCart(List<Long> ids,HttpServletRequest request);
	
	/**
	 * 创建订单具体信息
	 * @param param
	 * @param request
	 * @return
	 */
	Result createOrder(OrderParam param,HttpServletRequest request);
}
