package com.ssm.manager.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.commons.pojo.EasyUIDataGrid;
import com.ssm.commons.pojo.Result;
import com.ssm.manager.service.TbItemParamService;

@Controller
public class TbItemParamController {
	@Resource
	private TbItemParamService tbItemParamServiceImpl;
	
	/**
	 * 查询对应的类目是否存在类目模板，一个类目只能有一个类目模板
	 * @param catId
	 * @return
	 */
	@RequestMapping("/item/param/query/itemcatid/{catId}")
	@ResponseBody
	public Result selItemParam(@PathVariable long catId) {
		return tbItemParamServiceImpl.selParam(catId);
	}
	
	/**
	 * 分页显示类目对应的类目模板
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/item/param/list")
	@ResponseBody
	public EasyUIDataGrid showParamList(int page,int rows) {
		return tbItemParamServiceImpl.selParamList(page, rows);
	}
	
	@RequestMapping("/item/param/delete")
	@ResponseBody
	public Result delParam(String ids) {
		return tbItemParamServiceImpl.delParams(ids);
	}
	
	@RequestMapping("/item/param/save/{catId}")
	@ResponseBody
	public Result insParam(String paramData,@PathVariable long catId) {
		return tbItemParamServiceImpl.insParam(paramData, catId);
	}
}
