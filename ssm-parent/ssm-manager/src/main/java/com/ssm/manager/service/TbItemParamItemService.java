package com.ssm.manager.service;

import com.ssm.commons.pojo.Result;

public interface TbItemParamItemService {
	/**
	 * 根据商品id,查询商品具体的规格参数
	 * @param itemId
	 * @return
	 */
	Result selParamItem(long itemId);
		
}
