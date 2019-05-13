package com.ssm.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ssm.dubbo.service.TbItemCatDubboService;
import com.ssm.mapper.TbItemCatMapper;
import com.ssm.pojo.TbItemCat;
import com.ssm.pojo.TbItemCatExample;

public class TbItemCatDubboServiceImpl implements TbItemCatDubboService{
	@Resource
	private TbItemCatMapper tbItemCatMapper;
		
	@Override
	public List<TbItemCat> selCatList(long parentId) {
		TbItemCatExample example = new TbItemCatExample();
		example.createCriteria().andParentIdEqualTo(parentId);
		return tbItemCatMapper.selectByExample(example);
	}

}
