package com.ssm.dubbo.service;

import com.ssm.commons.pojo.EasyUIDataGrid;
import com.ssm.pojo.TbItemParam;

public interface TbItemParamDubboService {
	/**
	 * 根据类目id，查询商品规则参数模板
	 * @param catId
	 * @return
	 */
	TbItemParam selByCatId(long catId);
	
	/**
	 * 分页显示规格参数模板信息
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid selParamList(int page,int rows);
	
	/**
	 * 删除
	 * @param itemParam
	 * @return
	 */
	int delParam(long id)throws Exception;
	
	/**
	 * 新增商品规格模板
	 * @param itemParam
	 * @return
	 */
	int insParam(TbItemParam itemParam)throws Exception;
}
