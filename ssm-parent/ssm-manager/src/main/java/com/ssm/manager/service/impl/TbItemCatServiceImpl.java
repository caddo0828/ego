package com.ssm.manager.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ssm.commons.pojo.TreeNode;
import com.ssm.dubbo.service.TbItemCatDubboService;
import com.ssm.manager.service.TbItemCatService;
import com.ssm.pojo.TbItemCat;

@Service
public class TbItemCatServiceImpl implements TbItemCatService{
	@Reference
	private TbItemCatDubboService tbItemCatDubboServiceImpl;
	
	@Override
	public List<TreeNode> selCatList(long id) {
		List<TreeNode> nodesList = new ArrayList<>();
		
		List<TbItemCat> list = tbItemCatDubboServiceImpl.selCatList(id);
		if(list!=null&&list.size()>0) {
			for (TbItemCat tbItemCat : list) {
				TreeNode node = new TreeNode();
				node.setId(tbItemCat.getId());
				node.setText(tbItemCat.getName());
				//是父节点就关闭，不是就展开
				node.setState(tbItemCat.getIsParent()?"closed":"open");
				nodesList.add(node);
			}
		}
		return nodesList;
	}

}
