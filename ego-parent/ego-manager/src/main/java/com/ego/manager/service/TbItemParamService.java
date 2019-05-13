package com.ego.manager.service;

import com.ego.common.pojo.EasyUIDataGrid;
import com.ego.common.pojo.Result;

public interface TbItemParamService {
	/**
	 * 对规格参数表进行分页处理
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid selItemParamList(int page,int rows);
	
	/**
	 * 批量删除规格参数
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	Result delByparamId(String ids)throws Exception;
	
	/**
	 * 根据类目catId查询是否已经存在类目信息
	 * @param catId
	 * @return
	 */
	Result selByCatId(long catId);
	
	/**
	 * 根据传递的商品类目id，商品参数规格信息，新增商品规则参数数据
	 * @param catId
	 * @param paramDate
	 * @return
	 */
	Result insItemParam(long catId,String paramData);
}
