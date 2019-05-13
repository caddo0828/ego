package com.ssm.dubbo.service;

import com.ssm.pojo.TbItemDesc;

public interface TbItemDescDubboService {
	/**
	 * 根据商品id,查询商品描述对象
	 * @param ItemId
	 * @return
	 */
	TbItemDesc selByItemId(long ItemId);
	
}
