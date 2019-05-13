package com.ssm.manager.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ssm.commons.pojo.EasyUIDataGrid;
import com.ssm.commons.pojo.Result;
import com.ssm.commons.utils.IDUtils;
import com.ssm.dubbo.service.TbItemDubboService;
import com.ssm.dubbo.service.TbItemParamItemDubboService;
import com.ssm.manager.service.TbItemService;
import com.ssm.pojo.TbItem;
import com.ssm.pojo.TbItemDesc;
import com.ssm.pojo.TbItemParamItem;

@Service
public class TbItemServiceImpl implements TbItemService{
	@Reference
	private TbItemDubboService tbItemDubboServiceImpl;
	@Reference
	private TbItemParamItemDubboService tbItemParamItemDubboServiceImpl;
	
	
	@Override
	public EasyUIDataGrid selItemList(int page, int rows) {
		return tbItemDubboServiceImpl.selItemList(page, rows);
	}

	@Override
	public Result updItem(String ids, byte status) {
		Result result = new Result();
		String[] idsArray = ids.split(",");
		int index = 0;
		Date date = new Date();
		for (String id : idsArray) {
			TbItem item = new TbItem();
			item.setId(Long.parseLong(id));
			item.setStatus(status);
			item.setUpdated(date);
			try {
				index += tbItemDubboServiceImpl.updItems(item);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(index==idsArray.length) {
			result.setStatus(200);
		}
		return result;
	}

	@Override
	public Result updItemDescParam(TbItem item, String desc, String itemParams) {
		Result result = new Result();
		Date date = new Date();
		//修该商品对象， 商品修改时间
		item.setUpdated(date);
		
		//获取描述对象
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(date);
		
		
		//获取商品具体规格参数对象
		//先判断是否已经存在了该商品所对应的具体商品规格对象，不存在则创建，存在直接修改对应的数据
		TbItemParamItem itemParamItem = tbItemParamItemDubboServiceImpl.selByItemId(item.getId());
		if(itemParamItem!=null) {
			itemParamItem.setParamData(itemParams);
			itemParamItem.setUpdated(date);	
		}else {
			//由于通过主键进行更新数据，此时主键id不能通过自动递增设置，只能手动设置
			itemParamItem = new TbItemParamItem();
			itemParamItem.setId(IDUtils.getItemId());
			itemParamItem.setItemId(item.getId());
			itemParamItem.setParamData(itemParams);
			itemParamItem.setCreated(date);
			itemParamItem.setUpdated(date);
		}
		
		try {
			 int index  = tbItemDubboServiceImpl.updItemDescParam(item, itemDesc, itemParamItem);
			 if(index>0) {
				 //三个表修改成功
				 result.setStatus(200);
			 }
		} catch (Exception e) {
			e.printStackTrace();
			result.setData(e.getMessage());
		}
		
		return result;
	}

	@Override
	public Result insItemDescParam(TbItem item ,String desc , String itemParams) {
		//设置商品表信息
		long itemId = IDUtils.getItemId();
		Date date = new Date();
		item.setId(itemId);
		item.setCreated(date);
		item.setUpdated(date);
		
		//设置商品描述表信息
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParams);
		itemParamItem.setCreated(date);
		itemParamItem.setUpdated(date);
		
		Result result = new Result();
		try {
			int index = tbItemDubboServiceImpl.insItemDescParam(item, itemDesc, itemParamItem);
			if(index >0) {
				//新增三个表成功
				result.setStatus(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setData(e.getMessage());
		}
		
		return result;
	}

	
	
}
