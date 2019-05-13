package com.ssm.manager.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ssm.commons.pojo.Result;
import com.ssm.dubbo.service.TbItemDescDubboService;
import com.ssm.manager.service.TbItemDescService;
import com.ssm.pojo.TbItemDesc;

@Service
public class TbItemDescServiceImpl implements TbItemDescService{
	@Reference
	private TbItemDescDubboService tbItemDescDubboServiceImpl;
	
	@Override
	public Result selDescById(long itemId) {
		Result result = new Result();
		TbItemDesc tbItemDesc = tbItemDescDubboServiceImpl.selByItemId(itemId);
		if(tbItemDesc!=null) {
			result.setStatus(200);
			result.setData(tbItemDesc);
		}
		return result;
	}

}
