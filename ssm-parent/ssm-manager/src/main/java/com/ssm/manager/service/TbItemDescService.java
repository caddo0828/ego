package com.ssm.manager.service;

import com.ssm.commons.pojo.Result;

public interface TbItemDescService {
	/**
	 * 根据商品id,查询商品描述信息
	 * @param itemId
	 * @return
	 */
	Result selDescById(long itemId);
	
}
