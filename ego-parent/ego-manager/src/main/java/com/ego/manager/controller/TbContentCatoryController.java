package com.ego.manager.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.common.pojo.Result;
import com.ego.common.pojo.TreeNode;
import com.ego.manager.service.TbContentCategoryService;
import com.ego.pojo.TbContentCategory;

@Controller
public class TbContentCatoryController {
	@Resource
	private TbContentCategoryService tbContentCategoryServiceImpl;
	
	/**
	 * 分页技术显示内容分类列表
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<TreeNode> getCategoryNodeList(@RequestParam(value="id",defaultValue="0")long parentId) {
		return tbContentCategoryServiceImpl.selCategroyList(parentId);
	}
	
	/**
	 * 内容分类新增
	 * @param category
	 * @return
	 */
	@RequestMapping("/content/category/create")
	@ResponseBody
	public Result create(TbContentCategory category) {
		return tbContentCategoryServiceImpl.insCategroy(category);
	}
	
	/**
	 * 修改内容分类
	 * @param category
	 * @return
	 */
	@RequestMapping("/content/category/update")
	@ResponseBody
	public Result update(TbContentCategory category) {
		return tbContentCategoryServiceImpl.updCategroy(category);
	}
	
	/**
	 * 内容分类删除
	 * @return
	 */
	@RequestMapping("/content/category/delete/")
	@ResponseBody
	public Result delete(long id) {
		return tbContentCategoryServiceImpl.delContentCate(id);
	}
	
}

