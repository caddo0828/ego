package com.ego.manager.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.common.pojo.EasyUIDataGrid;
import com.ego.common.pojo.Result;
import com.ego.manager.service.TbItemParamService;

@Controller
public class TbItemParamController {
	@Resource
	private TbItemParamService tbItemParamServiceImpl;
	
	/**
	 * 查询商品参数
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/item/param/list")
	@ResponseBody
	public EasyUIDataGrid getItemParamList(int page,int rows) {
		return tbItemParamServiceImpl.selItemParamList(page, rows);
	}	
	
	/**
	 * 批量删除参数信息
	 * @param ids
	 * @return
	 */
	@RequestMapping("/item/param/delete")
	@ResponseBody
	public Result delById(String ids) {
		Result result = new Result();
		try {
			result = tbItemParamServiceImpl.delByparamId(ids);
		} catch (Exception e) {
			e.printStackTrace();
			result.setData(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 根据类目catId,判断是否已经存在类目信息
	 * @param catId
	 * @return
	 */
	@RequestMapping("/item/param/query/itemcatid/{catId}")
	@ResponseBody
	public Result selByCatId(@PathVariable long catId) {
		return  tbItemParamServiceImpl.selByCatId(catId);	
	}
	
	@RequestMapping("/item/param/save/{catId}")
	@ResponseBody
	public Result insItemParam(@PathVariable long catId,String paramData) {
		return tbItemParamServiceImpl.insItemParam(catId, paramData);
	}
}
