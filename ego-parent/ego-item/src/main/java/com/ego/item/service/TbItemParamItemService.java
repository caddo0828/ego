package com.ego.item.service;

public interface TbItemParamItemService {
	/**
	 * 通过商品id查询商品规格
	 * 前台要的数据是一个普通的字符串。并且普通字符串中是将数据转换成表格形式输出
	 * @param itemId
	 * @return
	 */
	String selParamByItemId(long itemId);
		
}
