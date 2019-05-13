package com.ssm.manager.service;

import java.util.List;
import com.ssm.commons.pojo.Result;
import com.ssm.commons.pojo.TreeNode;
import com.ssm.pojo.TbContentCategory;

public interface TbContentCategoryService {
	/**
	 * 根据父类目id，查询内容分类对象集合
	 * @param parentId
	 * @return
	 */
	List<TreeNode> selCatList(long parentId);
	
	/**
	 * 新增内容分类
	 * @param category
	 * @return
	 */
	Result insConteCate(TbContentCategory category);
	
	/**
	 * 重命名内容分类类目
	 * @param category
	 * @return
	 */
	Result updConteCate(TbContentCategory category);
	
	/**
	 * 根据类目删除对应类目，并且判断父类目下是否还存在子类目，存在则修改父类目状态，否则则不修改
	 * @param id
	 * @return
	 */
	Result delConteCate(long id);
}
