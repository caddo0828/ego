package com.ssm.dubbo.service;

import com.ssm.pojo.TbItemParamItem;

public interface TbItemParamItemDubboService {
	/**
	 * 根据商品id，查询商品具体规格对象
	 * @param itemId
	 * @return
	 */
	TbItemParamItem selByItemId(long itemId);
	
}
