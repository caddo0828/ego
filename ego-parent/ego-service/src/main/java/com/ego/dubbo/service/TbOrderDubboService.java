package com.ego.dubbo.service;

import java.util.List;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

public interface TbOrderDubboService {
	/**
	 * 同时新增订单表，订单-商品表，收货人信息表
	 * @param order
	 * @param orderItems
	 * @param orderShipping
	 * @return
	 * @throws Exception
	 */
	int insOrderParam(TbOrder order,List<TbOrderItem> orderItems,TbOrderShipping orderShipping)throws Exception;
}
