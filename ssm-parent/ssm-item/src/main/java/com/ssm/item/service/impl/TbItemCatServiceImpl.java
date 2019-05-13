package com.ssm.item.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.ssm.dubbo.service.TbItemCatDubboService;
import com.ssm.item.pojo.PortalMenu;
import com.ssm.item.pojo.PortalMenuNode;
import com.ssm.item.service.TbItemCatService;
import com.ssm.pojo.TbItemCat;

@Service
public class TbItemCatServiceImpl implements TbItemCatService{
	@Reference
	private TbItemCatDubboService tbItemCatDubboServiceImpl;

	@Override
	public PortalMenu getAllMenu() {
		//首先查出所有的一级商品分类
		List<TbItemCat> catList = tbItemCatDubboServiceImpl.selCatList(0);
		//根据一级菜单下的商品，判断子类商品是否是父类节点，是，则继续查询（循环遍历的过程）该父类目下的所有子类目信息
		PortalMenu menu = new PortalMenu();
		menu.setData(showAllMenuNode(catList));
		return menu;
	}

	
	/**
	 * 执行过程，根据所有父类目，查询所有子类目下的具体信息，
	 * 判断子类目是否是一个父节点，是的话，在接着遍历该子类目得到子类目下的所有更小级子类目
	 * 执行过程 （1） （2） 表示
	 * @param catList
	 * @return
	 */
	private List<Object> showAllMenuNode(List<TbItemCat> catList) {
		List<Object> nodeList = new ArrayList<>();
		for (TbItemCat tbItemCat : catList) {
			if(tbItemCat.getIsParent()) {
				//是父类目，则i属性中包含了 u,n ,i 属性
				PortalMenuNode node = new PortalMenuNode();
				// （1） (1.1)
				node.setU("/products/"+tbItemCat.getId()+".html");
				// (2) (1.2)
				node.setN("<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
				// (3) (1.3) 
				node.setI(showAllMenuNode(tbItemCatDubboServiceImpl.selCatList(tbItemCat.getId())));
				// (4)
				nodeList.add(node);
			}else {
				//不是父类目，则i属性为 String类型的集合
				//(1.3)
				nodeList.add("/products/"+tbItemCat.getId()+".html|"+tbItemCat.getName());
			}
		}
		//(5)
		return nodeList;
	}

	
	

}
