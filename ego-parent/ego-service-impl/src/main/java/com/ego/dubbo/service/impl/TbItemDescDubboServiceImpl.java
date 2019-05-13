package com.ego.dubbo.service.impl;

import javax.annotation.Resource;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.mapper.TbItemDescMapper;
import com.ego.pojo.TbItemDesc;

public class TbItemDescDubboServiceImpl implements TbItemDescDubboService{
	@Resource
	private TbItemDescMapper tbItemDescMapper;
	
	@Override
	public int insItemDesc(TbItemDesc itemDesc) {
		//修改值不为空的数据
		return tbItemDescMapper.insertSelective(itemDesc);
	}

	@Override
	public TbItemDesc selByItemId(long itemId) {
		return tbItemDescMapper.selectByPrimaryKey(itemId);
	}

}
