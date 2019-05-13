package com.ssm.manager.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ssm.commons.pojo.EasyUIDataGrid;
import com.ssm.commons.pojo.Result;
import com.ssm.dubbo.service.TbItemParamDubboService;
import com.ssm.manager.service.TbItemParamService;
import com.ssm.pojo.TbItemParam;

@Service
public class TbItemParamServiceImpl implements TbItemParamService{
	@Reference
	private TbItemParamDubboService tbItemParamDubboServiceImpl;
	
	@Override
	public Result selParam(long catId) {
		Result result = new Result();
		//商品类目模板对象
		TbItemParam tbItemParam = tbItemParamDubboServiceImpl.selByCatId(catId);
		if(tbItemParam!=null) {
			//表示存在商品模板
			result.setStatus(200);
			result.setData(tbItemParam);
		}
		return result;
	}

	@Override
	public EasyUIDataGrid selParamList(int page, int rows) {
		return tbItemParamDubboServiceImpl.selParamList(page, rows);
	}

	@Override
	public Result delParams(String ids) {
		//前台传递的是以,进行拼接的字符串
		String[] idsArray = ids.split(",");
		int index = 0;
		for (String id : idsArray) {
			try {
				index += tbItemParamDubboServiceImpl.delParam(Long.parseLong(id));
			}catch (Exception e) {
				e.printStackTrace();
			}	
		}
		Result result = new Result();
		if(index==idsArray.length) {
			result.setStatus(200);
		}else {
			result.setData("类目模板删除失败，数据回滚");
		}
		
		return result;
	}

	@Override
	public Result insParam(String paramData, long catId) {
		TbItemParam itemParam = new TbItemParam();
		Date date = new Date();
		itemParam.setItemCatId(catId);
		itemParam.setParamData(paramData);
		itemParam.setCreated(date);
		itemParam.setUpdated(date);
		
		Result result = new Result();
		try {
			int index = tbItemParamDubboServiceImpl.insParam(itemParam);
			if(index==1) {
				result.setStatus(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
