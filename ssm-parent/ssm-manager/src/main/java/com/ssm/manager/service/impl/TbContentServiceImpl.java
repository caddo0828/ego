package com.ssm.manager.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.ssm.commons.pojo.EasyUIDataGrid;
import com.ssm.commons.pojo.Result;
import com.ssm.dubbo.service.TbContentDubboService;
import com.ssm.manager.service.TbContentService;
import com.ssm.pojo.TbContent;

@Service
public class TbContentServiceImpl implements TbContentService{
	@Reference
	private TbContentDubboService tbContentDubboServiceImpl;
	
	@Override
	public EasyUIDataGrid showContentList(long categoryId, int page, int rows) {
		return tbContentDubboServiceImpl.selContentList(categoryId, page, rows);
	}

	@Override
	public Result updContent(TbContent content) {
		Result result = new Result();
		Date date = new Date();
		content.setUpdated(date);
		try {
			int index = tbContentDubboServiceImpl.updContent(content);
			if(index==1) {
				result.setStatus(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Result delContents(String ids) {
		String[] idsArray = ids.split(",");
		int index = 0;
		for (String id : idsArray) {
			try {
				index += tbContentDubboServiceImpl.delContents(Long.parseLong(id));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Result result = new Result();
		if(index==idsArray.length) {
			result.setStatus(200);
		}else {
			result.setData("删除内容失败，请重新操作");
		}
		
		return result;
	}

	@Override
	public Result insConent(TbContent content) {
		Date date = new Date();
		content.setCreated(date);
		content.setUpdated(date);
		Result result = new Result();
		try {
			int index = tbContentDubboServiceImpl.insContent(content);
			if(index==1) {
				result.setStatus(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
