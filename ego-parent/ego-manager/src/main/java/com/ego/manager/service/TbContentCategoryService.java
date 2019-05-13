package com.ego.manager.service;

import java.util.List;

import com.ego.common.pojo.Result;
import com.ego.common.pojo.TreeNode;
import com.ego.pojo.TbContentCategory;

public interface TbContentCategoryService {
	/**
	 * 查询出所有的内容分类类目
	 * @param parentId
	 * @return
	 */
	List<TreeNode> selCategroyList(long parentId);
	
	/**
	 * 新增内容分类
	 * @param category
	 * @return
	 */
	Result insCategroy(TbContentCategory category);
	
	/**
	 * 修改内容分类
	 * @param category
	 * @return
	 */
	Result updCategroy(TbContentCategory category);
	
	/**
	 * 根据id删除对应的内容分类
	 * @param id
	 * @return
	 */
	Result delContentCate(long id);
}
