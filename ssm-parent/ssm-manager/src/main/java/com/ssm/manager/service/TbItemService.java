package com.ssm.manager.service;

import com.ssm.commons.pojo.EasyUIDataGrid;
import com.ssm.commons.pojo.Result;
import com.ssm.pojo.TbItem;
import com.ssm.pojo.TbItemDesc;
import com.ssm.pojo.TbItemParamItem;

public interface TbItemService {
	/**
	 * 分页查询商品列表
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid selItemList(int page,int rows);
	
	/**
	 * 修改商品的状态（物理删除，下架，上架）
	 * @param ids
	 * @param status
	 * @return
	 */
	Result updItem(String ids,byte status);
	
	/**
	 * 商品修改。同时修改商品表，商品描述表，商品具体规格参数表
	 * @param item
	 * @param desc
	 * @param itemParams
	 * @return
	 */
	Result updItemDescParam(TbItem item ,String desc , String itemParams);
	
	/**
	 * 新增商品表，商品描述表，商品具体规格参数表
	 * @param item
	 * @param itemDesc
	 * @param itemParamItem
	 * @return
	 */
	Result insItemDescParam(TbItem item ,String desc , String itemParams);
	
}
