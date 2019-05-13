package com.ssm.dubbo.service;

import java.util.List;

import com.ssm.pojo.TbContentCategory;

public interface TbContentCategoryDubboService {
	/**
	 * 根据父类目id查询所有内容分类信息
	 * @param parentId
	 * @return
	 */
	List<TbContentCategory> selCatList(long parentId);
	
	/**
	 * 新增内容分类
	 * @param category
	 * @return
	 */
	int insContentCat(TbContentCategory category) throws Exception ;
	
	/**
	 * 修改内容分类
	 * @param category
	 * @return
	 */
	int updContentCat(TbContentCategory category)throws Exception ;
	
	/**
	 * 通过主键查询类目对象
	 * @param id
	 * @return
	 */
	TbContentCategory selById(long id);

	
}
