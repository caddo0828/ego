package com.ego.dubbo.service;

import java.util.List;

import com.ego.common.pojo.EasyUIDataGrid;
import com.ego.pojo.TbContent;


public interface TbContentDubboService {
	/**
	 * 根据分类id，查询出同类型的内容数据，并且进行分页显示
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid selContentList(long categoryId,int page,int rows);
 
	/**
	 * 删除内容
	 * @param id
	 * @return
	 */
	int delByIds(long id)throws Exception;
	
	/**
	 * 修改内容
	 * @param content
	 * @return
	 */
	int updContent(TbContent content)throws Exception;
	
	/**
	 * 内容新增
	 * @param content
	 * @return
	 */
	int insContent(TbContent content)throws Exception;
	
	/**
	 * 查询大广告数据，条件：查询全部、按照排序进行，同时满足条件
	 * 可以实现查询的条件二选一，但是一个方法实现
	 * @param count
	 * @param isSort
	 * @return
	 */
	List<TbContent> selByCount(int count , boolean isSort);
}
