package com.ssm.manager.service;

import java.util.List;

import com.ssm.commons.pojo.TreeNode;


public interface TbItemCatService {
	/**
	 * 根据节点的父节点ID查询对应的类目
	 * @param id
	 * @return
	 */
	List<TreeNode> selCatList(long id);
	
}
