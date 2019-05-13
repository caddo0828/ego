package com.ego.item.service;

import com.ego.common.pojo.TbItemChild;

public interface TbItemService {
	/**
	 * 根据id显示商品详情
	 * @param id
	 * @return
	 */
	TbItemChild showDetails(long id);
	
}
