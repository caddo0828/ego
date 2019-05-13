package com.ego.manager.service;

import java.util.List;

import com.ego.common.pojo.TreeNode;

public interface TbItemCatService {
	/**
	 * 根据父类目parentId查询出所有的类目数据，并且判断当前树节点是不是父节点
	 * 根据是否是父节点，进行叶子结点状态的修改
	 * @param parentId
	 * @return  返回标准的树形节点的对象id ,text,state
	 */
	List<TreeNode> selTbItemCatList(long parentId);
}
