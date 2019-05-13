package com.ego.dubbo.service;

import com.ego.pojo.TbItemDesc;

public interface TbItemDescDubboService {
	/**
	 * 新增商品描述信息
	 * @param itemDesc
	 * @return
	 */
	int insItemDesc(TbItemDesc itemDesc);
	
	/**
	 * 通过商品的id,查询商品描述对象
	 * @param itemId
	 * @return
	 */
	TbItemDesc selByItemId(long itemId);
}
