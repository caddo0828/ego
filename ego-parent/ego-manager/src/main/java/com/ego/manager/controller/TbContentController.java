package com.ego.manager.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.common.pojo.EasyUIDataGrid;
import com.ego.common.pojo.Result;
import com.ego.manager.service.TbContentService;
import com.ego.pojo.TbContent;

@Controller
public class TbContentController {
	@Resource
	private TbContentService tbContentServiceImpl;
	
	/**
	 * 内容查询
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIDataGrid getList(long categoryId,int page,int rows) {
		return tbContentServiceImpl.selContentList(categoryId, page, rows);
	}
	
	/**
	 * 批量删除内容
	 * @param ids
	 * @return
	 */
	@RequestMapping("/content/delete")
	@ResponseBody
	public Result delete(String ids) {
		return tbContentServiceImpl.delByIds(ids);
	}
	
	@RequestMapping("/rest/content/edit")
	@ResponseBody
	public Result update(TbContent content) {
		return tbContentServiceImpl.updContent(content);
	}
	
	@RequestMapping("/content/save")
	@ResponseBody
	public Result save(TbContent content) {
		return tbContentServiceImpl.insContent(content);
	}
}
