package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.mapper.TbItemCatMapper;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemCatExample;

public class TbItemCatDubboServiceImpl implements TbItemCatDubboService{
	@Resource
	private TbItemCatMapper tbItemCatMapper;
	
	@Override
	public List<TbItemCat> selItemCatList(long parentId) {
		//由于此时的类目不是主键，因此要自定义查询条件
		TbItemCatExample example = new TbItemCatExample();
		example.createCriteria().andParentIdEqualTo(parentId);
		return tbItemCatMapper.selectByExample(example);
	}

	@Override
	public TbItemCat selByCatId(long itemCatId) {
		return tbItemCatMapper.selectByPrimaryKey(itemCatId);
	}

}
