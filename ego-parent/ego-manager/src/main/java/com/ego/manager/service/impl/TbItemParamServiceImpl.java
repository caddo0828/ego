package com.ego.manager.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.common.pojo.EasyUIDataGrid;
import com.ego.common.pojo.Result;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.manager.service.TbItemParamService;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamChild;

@Service
public class TbItemParamServiceImpl implements TbItemParamService{
	@Reference
	private TbItemParamDubboService tbItemParamDubboServiceImpl;
	@Reference
	private TbItemCatDubboService tbItemCatDubboServiceImpl;
	
	@Override
	public EasyUIDataGrid selItemParamList(int page, int rows) {
		EasyUIDataGrid dataGrid = tbItemParamDubboServiceImpl.selItemParamList(page, rows);
		List<TbItemParam> paramList = (List<TbItemParam>) dataGrid.getRows();
		List<TbItemParamChild> childList = new ArrayList<>();
		for (TbItemParam tbItemParam : paramList) {
			TbItemParamChild child = new TbItemParamChild();
			child.setId(tbItemParam.getId());
			child.setItemCatId(tbItemParam.getItemCatId());
			child.setParamData(tbItemParam.getParamData());
			child.setCreated(tbItemParam.getCreated());
			child.setUpdated(tbItemParam.getUpdated());
			child.setItemCatName( (tbItemCatDubboServiceImpl.selByCatId(tbItemParam.getItemCatId())).getName());
			childList.add(child);
		}
		dataGrid.setRows(childList);
		return dataGrid;
	}

	
	@Override
	public Result delByparamId(String ids) throws Exception {
		Result result = new Result();
		//前台返回的批量删除的规格参数id是一个以，拼接的字符串
		String[] idsArray = ids.split(",");
		int index = 0;
		for (String id : idsArray) {
			 index += tbItemParamDubboServiceImpl.delItemParams(Long.parseLong(id));
		}
		if(index==idsArray.length) {
			result.setStatus(200);
			return result;
		}else {
			throw new Exception("删除规格参数删除失败，数据还原!");
		}
	}


	@Override
	public Result selByCatId(long catId) {
		Result result = new Result();
		TbItemParam itemParam = tbItemParamDubboServiceImpl.selByCatId(catId);
		if(itemParam!=null) {
			//表示该商品已经有规格参数信息
			result.setStatus(200);
			result.setData(itemParam);
		}
		return result;
	}


	@Override
	public Result insItemParam(long catId, String paramData) {
		Result result = new Result();
		TbItemParam itemParam = new TbItemParam();
		Date date = new Date();
		itemParam.setItemCatId(catId);
		itemParam.setParamData(paramData);
		itemParam.setCreated(date);
		itemParam.setUpdated(date);
		int index = tbItemParamDubboServiceImpl.insItemParam(itemParam);
		if(index==1) {
			result.setStatus(200);
		}
		return result;
	}
}