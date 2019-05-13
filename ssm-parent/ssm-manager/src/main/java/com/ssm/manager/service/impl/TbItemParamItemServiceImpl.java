package com.ssm.manager.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ssm.commons.pojo.Result;
import com.ssm.dubbo.service.TbItemParamItemDubboService;
import com.ssm.manager.service.TbItemParamItemService;
import com.ssm.pojo.TbItemParamItem;

@Service
public class TbItemParamItemServiceImpl implements TbItemParamItemService{
	@Reference
	private TbItemParamItemDubboService tbItemParamItemDubboServiceImpl;
	
	@Override
	public Result selParamItem(long itemId) {
		Result result = new Result();
		TbItemParamItem paramItem = tbItemParamItemDubboServiceImpl.selByItemId(itemId);
		if(paramItem!=null) {
			//代表该商品包含具体的规格参数信息
			result.setStatus(200);
			result.setData(paramItem);
		}
		return result;
	}

}
