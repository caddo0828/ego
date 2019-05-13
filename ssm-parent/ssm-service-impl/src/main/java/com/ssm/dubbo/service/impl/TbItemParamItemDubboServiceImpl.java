package com.ssm.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ssm.dubbo.service.TbItemParamItemDubboService;
import com.ssm.mapper.TbItemParamItemMapper;
import com.ssm.pojo.TbItemParamItem;
import com.ssm.pojo.TbItemParamItemExample;

public class TbItemParamItemDubboServiceImpl implements TbItemParamItemDubboService{
	@Resource
	private TbItemParamItemMapper tbItemParamItemMapper;
	
	@Override
	public TbItemParamItem selByItemId(long itemId) {
		TbItemParamItemExample example = new TbItemParamItemExample();
		example.createCriteria().andItemIdEqualTo(itemId);
		List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
		if(list!=null&&list.size()>0) {
			//一个商品只能对应一个具体的规格参数，因此查询出的集合实际上只有具体规格对象
			return list.get(0);
		}
		return null;
	}

}
