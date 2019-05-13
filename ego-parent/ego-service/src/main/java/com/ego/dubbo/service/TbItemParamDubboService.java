package com.ego.dubbo.service;

import com.ego.common.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItemParam;

public interface TbItemParamDubboService {
	/**
	 * 对规格参数表分页
	 * @param page 分页页数
	 * @param rows 一页显示多少条数据
	 * @return 
	 */
	EasyUIDataGrid selItemParamList(int page,int rows);
	
	/**
	 * 根据id批量删除规格参数
	 * @param id
	 * @return
	 */
	int delItemParams(long id) throws Exception;
	
	/**
	 * 根据商品类目id查询是否存在类目对象
	 * @param itemCatId
	 * @return
	 */
	TbItemParam selByCatId(long itemCatId);
	
	/**
	 * 新增商品规格参数
	 * @param itemParam
	 * @return
	 */
	int insItemParam(TbItemParam itemParam);
}
