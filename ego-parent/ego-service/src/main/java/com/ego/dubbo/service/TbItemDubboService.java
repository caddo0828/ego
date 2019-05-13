package com.ego.dubbo.service;

import java.util.List;

import com.ego.common.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;

public interface TbItemDubboService {
	/**
	 * 分页查询商品列表
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid getTbItemList(int page ,int rows);
	
	/**
	 * 修改商品状态，上架，下架，删除操作
	 * @param item
	 * @return
	 */
	int updTbItem(TbItem item)throws Exception;
	
	/**
	 * 新增商品
	 * @param item
	 * @return
	 */
	int insTbItem(TbItem item);
	
	/**
	 * 同时新增商品表数据和商品描述表数据,以及商品具体参数表(考虑事务回滚)
	 * @param item
	 * @param ItemDesc
	 * @param itemParamItem
	 * @return
	 * @throws Exception
	 */
	int insTbItemAndDesc(TbItem item ,TbItemDesc ItemDesc,TbItemParamItem itemParamItem)throws Exception;
	
	/**
	 * 通过状态查询全部tbItem表可用数据
	 * @param status
	 * @return
	 */
	List<TbItem>  selAllByStatu(byte status);
	
	/**
	 * 通过商品id查询商品对象
	 * @param id
	 * @return
	 */
	TbItem selById(long id);
} 
