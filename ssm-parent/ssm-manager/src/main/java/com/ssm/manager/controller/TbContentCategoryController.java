package com.ssm.manager.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.commons.pojo.Result;
import com.ssm.commons.pojo.TreeNode;
import com.ssm.manager.service.TbContentCategoryService;
import com.ssm.pojo.TbContentCategory;

@Controller
public class TbContentCategoryController {
	@Resource
	private TbContentCategoryService tbContentCategoryServiceImpl;
	
	/**
	 * 获取内容分类的树型节点集合
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<TreeNode> showCateList(@RequestParam(value="id",defaultValue="0")long parentId) {
		return tbContentCategoryServiceImpl.selCatList(parentId);
	}
	
	/**
	 * 新增内容分类
	 * @param category
	 * @return
	 */
	@RequestMapping("/content/category/create")
	@ResponseBody
	public Result insContentCate(TbContentCategory category) {
		return tbContentCategoryServiceImpl.insConteCate(category);
	}
	
	@RequestMapping("/content/category/update")
	@ResponseBody
	public Result update(TbContentCategory category) {
		return tbContentCategoryServiceImpl.updConteCate(category);
	}
	
	@RequestMapping("/content/category/delete/")
	@ResponseBody
	public Result delete(long id) {
		return tbContentCategoryServiceImpl.delConteCate(id);
	}
}
