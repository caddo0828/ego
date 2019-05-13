package com.ssm.dubbo.service;

import java.util.List;

import com.ssm.commons.pojo.EasyUIDataGrid;
import com.ssm.pojo.TbContent;

public interface TbContentDubboService {
	/**
	 * 根据内容类目id，分页查询出所有内容
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid  selContentList(long categoryId,int page,int rows);
	
	/**
	 * 修改内容数据
	 * @param content
	 * @return
	 */
	int updContent(TbContent content)throws Exception ;
	
	/**
	 * 删除内容数据
	 * @param id
	 * @return
	 */
	int delContents(long id)throws Exception;
	
	/**
	 * 新增内容
	 * @param content
	 * @return
	 */
	int insContent(TbContent content)throws Exception ;
	
	/**
	 * 自定义查询几个前台大广告顺序，按照什么进行排序查询
	 * @param count
	 * @param sort
	 * @return
	 */
	List<TbContent> selPicList(int count , boolean sort);
}
