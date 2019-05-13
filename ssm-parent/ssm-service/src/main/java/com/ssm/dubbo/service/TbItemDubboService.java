package com.ssm.dubbo.service;

import com.ssm.commons.pojo.EasyUIDataGrid;
import com.ssm.pojo.TbItem;
import com.ssm.pojo.TbItemDesc;
import com.ssm.pojo.TbItemParamItem;

/**
 * 商品服务接口
 * @author 老腰
 *
 */
public interface TbItemDubboService {
	/**
	 * 查询分页后的商品数据
	 * @param page 第几页
	 * @param rows 每页显示多少条数据
	 * @return
	 */
	EasyUIDataGrid selItemList(int page,int rows);
	
	/**
	 * 更新商品的状态，物理删除，下架，上架（提供事务回滚）
	 * @param item
	 * @return
	 * @throws Exception
	 */
	int updItems(TbItem item)throws Exception;
	
	/**
	 * 更新商品表。商品描述表。商品具体规格表
	 * @param Item
	 * @param itemDesc
	 * @param paramItem
	 * @return
	 */
	int updItemDescParam(TbItem Item,TbItemDesc itemDesc , TbItemParamItem paramItem)throws Exception;
	
	/**
	 * 新增商品，同时新增商品描述表，新增商品具体参数表（提供事务回滚）
	 * @param item
	 * @param itemDesc
	 * @param paramItem
	 * @return
	 */
	int insItemDescParam(TbItem item ,TbItemDesc itemDesc ,TbItemParamItem paramItem)throws Exception;
}
