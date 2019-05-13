package com.ego.dubbo.service.impl;

import java.util.List;
import javax.annotation.Resource;
import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.mapper.TbOrderItemMapper;
import com.ego.mapper.TbOrderMapper;
import com.ego.mapper.TbOrderShippingMapper;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

public class TbOrderDubboServiceImpl implements TbOrderDubboService{
    @Resource
	private TbOrderMapper tbOrderMapper;
    @Resource
	private TbOrderItemMapper tbOrderItemMapper;
    @Resource
    private TbOrderShippingMapper tbOrderShippingMapper;
    
	@Override
	public int insOrderParam(TbOrder order, List<TbOrderItem> orderItems, TbOrderShipping orderShipping) throws Exception {
		int index = tbOrderMapper.insertSelective(order);
		for (TbOrderItem tbOrderItem : orderItems) {
			index += tbOrderItemMapper.insertSelective(tbOrderItem);
		}
		index += tbOrderShippingMapper.insertSelective(orderShipping);
		
		if(index==2+orderItems.size()) {
			//全部数据新增成功
			return 1;
		}else {
			throw new Exception("商品订单创建失败");
		}
	}

}
