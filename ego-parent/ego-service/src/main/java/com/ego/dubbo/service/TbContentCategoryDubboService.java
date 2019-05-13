package com.ego.dubbo.service;

import java.util.List;

import com.ego.pojo.TbContentCategory;

public interface TbContentCategoryDubboService {
	/**（内容分类表）
	 * 根据父类目parentId查询所有子类目
	 * @param parentId
	 * @return
	 */
	List<TbContentCategory> getCategoryList(long parentId);
	
	/**
	 * 新增内容分类
	 * 新增的同时要判断该父类节点是否是一个父节点，如果是则不修改状态，如果不是则修改其父类isparent为true
                同一个分类下，不允许有同名的子分类 ，根据父类id查询父id 下的所有子类目，判断子类目中是否存在和修改的名称一样的分类，如果存在则增加失败
	     并且由于删除的数据是逻辑删除，那在查询所有的类目数据的时候，就只能查类目状态为1（正常）的数据
	 * @param category
	 * @return
	 */
	int insContentCate(TbContentCategory category)throws Exception;
		
	/**
	 * 修改内容分类信息
	 * @param category
	 * @return
	 */
	int updCategory(TbContentCategory category)throws Exception;
	
	/**
	 * 根据id查询当前内容分类
	 * @param id
	 * @return
	 */
	TbContentCategory selById(long id);
}
