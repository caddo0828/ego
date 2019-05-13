package com.ssm.manager.service;

import com.ssm.commons.pojo.EasyUIDataGrid;
import com.ssm.commons.pojo.Result;

public interface TbItemParamService {
	/**
	 * 根据商品类目id，查询类目的模板信息
	 * @param catId
	 * @return
	 */
	Result selParam(long catId);
	
	/**
	 * 分页查询规格参数
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid selParamList(int page,int rows);
	
	/**
	 * 批量删除类目模板数据
	 * @param ids
	 * @return
	 */
	Result delParams(String ids);
	
	/**
	 * 新增规格模板
	 * @param paramData
	 * @param catId
	 * @return
	 */
	Result insParam(String paramData, long catId);
}
