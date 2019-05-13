package com.ssm.manager.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.commons.pojo.EasyUIDataGrid;
import com.ssm.commons.pojo.Result;
import com.ssm.manager.service.TbItemService;
import com.ssm.pojo.TbItem;

@Controller
public class TbItemController {
	@Resource
	private TbItemService tbItemServiceImpl;
	
	/**
	 * 分页显示查询商品
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGrid showItemList(int page,int rows) {
		return tbItemServiceImpl.selItemList(page, rows);
	}
	
	/**
	 * 显示编辑商品页面
	 * @return
	 */
	@RequestMapping("/rest/page/item-edit")
	public String itemEditPage() {
		return "item-edit";
	}

	/**
	 * 批量删除商品
	 * @param ids
	 * @return
	 */
	@RequestMapping("/rest/item/delete")
	@ResponseBody
	public Result delItem(String ids) {
		return tbItemServiceImpl.updItem(ids, (byte)3);
	}
	
	/**
	 * 批量下架商品
	 * @param ids
	 * @return
	 */
	@RequestMapping("/rest/item/instock")
	@ResponseBody
	public Result instockItem(String ids) {
		return tbItemServiceImpl.updItem(ids, (byte)2);
	}
	
	/**
	 * 批量上架商品
	 * @param ids
	 * @return
	 */
	@RequestMapping("/rest/item/reshelf")
	@ResponseBody
	public Result reshelfItem(String ids) {
		return tbItemServiceImpl.updItem(ids, (byte)1);
	}
	
	/**
	 * 修改（编辑）商品信息，商品描述表，商品具体规格参数表
	 * @param item
	 * @param desc
	 * @param itemParams
	 * @return
	 */
	@RequestMapping("/rest/item/update")
	@ResponseBody
	public Result update(TbItem item , String desc , String itemParams) {
		return tbItemServiceImpl.updItemDescParam(item, desc, itemParams);
	}
	
	@RequestMapping("/item/save")
	@ResponseBody
	public Result save(TbItem item , String desc , String itemParams) {
		return tbItemServiceImpl.insItemDescParam(item, desc, itemParams);
	}
}
