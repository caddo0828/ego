package com.ego.dubbo.service;

import com.ego.pojo.TbItemParamItem;

public interface TbItemParamItemDubboService {
	/**
	 * 通过商品id查询具体的商品规格参数
	 * @param itemId
	 * @return
	 */
	TbItemParamItem selByItemId(long itemId);
		
}
