package com.ego.dubbo.service;

import java.util.List;

import com.ego.pojo.TbItemCat;

public interface TbItemCatDubboService {
	/**
	 * 根据父类目parentID查找出对应的所有子类目对象
	 * 由于父类目parentid = 类目id其实，就是自己查询自己的方法
	 * @param parentId
	 * @return
	 */
	List<TbItemCat> selItemCatList(long parentId);
	
	/**
	 * 根据类目id查询类目对象
	 * @param itemCatId
	 * @return
	 */
	TbItemCat selByCatId(long itemCatId);
}
