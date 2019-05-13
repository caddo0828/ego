package com.ego.item.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.item.pojo.PortalMenu;
import com.ego.item.pojo.PortalMenuNode;
import com.ego.item.service.TbItemCatService;
import com.ego.pojo.TbItemCat;

@Service
public class TbItemCatServiceImpl implements TbItemCatService{
	@Reference
	private TbItemCatDubboService tbItemCatDubboServiceImpl;
	
	@Override
	public PortalMenu gelAllMenu() {
		//查询出所有的一级菜单
		List<TbItemCat> list = tbItemCatDubboServiceImpl.selItemCatList(0);
		PortalMenu menu = new PortalMenu();
		//循环遍历查询出所有的子菜单，通过一级菜单查子菜单，由于父类目菜单与商品类目id一样
		//就是循环遍历调用自己的过程
		menu.setData(showChildMenu(list));
		return menu;
	}

	private List<Object> showChildMenu(List<TbItemCat> list) {
		List<Object> nodesList = new ArrayList<>();
		for (TbItemCat tbItemCat : list) {
			//判断是否是父级菜单
			if(tbItemCat.getIsParent()) {
				//此时传递的data数据中包含的object对象中包含u , n , i（集合对象）属性
				PortalMenuNode menuNode = new PortalMenuNode();
				menuNode.setU("/products/"+tbItemCat.getId()+".html");
				menuNode.setN("<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
				//此时的i是一个集合对象，集合对象同样包含了u,n,i属性，就是循环嵌套自己调用自己的过程
				//使用类目id查询对应的类目id
				//对i属性进行遍历获取
				menuNode.setI(showChildMenu(tbItemCatDubboServiceImpl.selItemCatList(tbItemCat.getId())));
				nodesList.add(menuNode);
			}else {
				//此时的菜单为子菜单，传递的i属性就为普通字符串
				nodesList.add("/products/"+tbItemCat.getId()+".html|"+tbItemCat.getName());
			}
		}
		return nodesList;
	}

}
