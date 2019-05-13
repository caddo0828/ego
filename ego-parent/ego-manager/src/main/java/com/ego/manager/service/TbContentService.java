package com.ego.manager.service;

import com.ego.common.pojo.EasyUIDataGrid;
import com.ego.common.pojo.Result;
import com.ego.pojo.TbContent;

public interface TbContentService {
	/**
	 * 分页查询内容数据
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid selContentList(long categoryId,int page,int rows);
	
	/**
	 * 根据id删除内容
	 * @param ids
	 * @return
	 */
	Result delByIds(String ids);
	
	/**
	 * 修改内容信息
	 * @param content
	 * @return
	 */
	Result updContent(TbContent content);
	
	/**
	 * 新增内容
	 * @param content
	 * @return
	 */
	Result insContent(TbContent content);
	
}
