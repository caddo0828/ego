package com.ego.item.service;

public interface TbItemDescService {
	/**
	 * 根据商品id查询商品的描述对象再查询商品描述具体信息
	 * @param id
	 * @return
	 */
   String selDescById(long id);
	
	
}
