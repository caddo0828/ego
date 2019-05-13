package com.ego.manager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.common.pojo.TreeNode;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.manager.service.TbItemCatService;
import com.ego.pojo.TbItemCat;

@Service
public class TbItemCatServiceImpl implements TbItemCatService{
	@Reference
	private TbItemCatDubboService tbItemCatDubboServiceImpl;
	
	@Override
	public List<TreeNode> selTbItemCatList(long parentId) {
		List<TreeNode> nodesList = new ArrayList<>();
		List<TbItemCat> itemCatList = tbItemCatDubboServiceImpl.selItemCatList(parentId);
		for (TbItemCat tbItemCat : itemCatList) {
			//循环遍历出所有的类目对象，获取数据存放在叶子节点中
			TreeNode node = new TreeNode();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent()?"closed":"open");
			nodesList.add(node);
		}
		return nodesList;
	}

}
