package com.ssm.manager.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.commons.pojo.EasyUIDataGrid;
import com.ssm.commons.pojo.Result;
import com.ssm.manager.service.TbContentService;
import com.ssm.pojo.TbContent;

@Controller
public class TbContentController {
	@Resource
	private TbContentService tbContentServiceImpl;
	
	/**
	 * 根据内容分类id，分页查询内容数据
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIDataGrid showList(long categoryId ,int page,int rows) {
		return tbContentServiceImpl.showContentList(categoryId, page, rows);
	}
	
	/**
	 * 修改内容数据
	 * @param content
	 * @return
	 */
	@RequestMapping("/rest/content/edit")
	@ResponseBody
	public Result editContent(TbContent content) {
		return tbContentServiceImpl.updContent(content);
	}
	
	/**
	 * 批量删除内容分类
	 * @param ids
	 * @return
	 */
	@RequestMapping("/content/delete")
	@ResponseBody
	public Result delete(String ids) {
		return tbContentServiceImpl.delContents(ids);
	}
	
	@RequestMapping("/content/save")
	@ResponseBody
	public Result insContent(TbContent content) {
		return tbContentServiceImpl.insConent(content);
	}
}
