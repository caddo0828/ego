package com.ssm.dubbo.service.impl;

import javax.annotation.Resource;

import com.ssm.dubbo.service.TbItemDescDubboService;
import com.ssm.mapper.TbItemDescMapper;
import com.ssm.pojo.TbItemDesc;

public class TbItemDescDubboServiceImpl implements TbItemDescDubboService{
	@Resource
	private TbItemDescMapper tbItemDescMapper;
	
	@Override
	public TbItemDesc selByItemId(long ItemId) {
		return tbItemDescMapper.selectByPrimaryKey(ItemId);
	}

}
