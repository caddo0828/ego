package com.ssm.manager.service;

import com.ssm.commons.pojo.EasyUIDataGrid;
import com.ssm.commons.pojo.Result;
import com.ssm.pojo.TbContent;

public interface TbContentService {
	/**
	 * 分页显示内容分类id下的所有内容分类数据
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid showContentList(long categoryId,int page ,int rows);
	
	/**
	 * 修改内容数据
	 * @param content
	 * @return
	 */
	Result updContent(TbContent content);
	
	/**
	 * 批量删除内容数据
	 * @param ids
	 * @return
	 */
	Result delContents(String ids);
	
	/**
	 * 新增内容分类
	 * @param content
	 * @return
	 */
	Result insConent(TbContent content);
}
