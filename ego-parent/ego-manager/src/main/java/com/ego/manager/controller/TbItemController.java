package com.ego.manager.controller;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ego.common.pojo.EasyUIDataGrid;
import com.ego.common.pojo.Result;
import com.ego.manager.service.TbItemService;
import com.ego.pojo.TbItem;

@Controller
public class TbItemController {
	@Resource
	private TbItemService tbItemServiceImpl;
	/**
	 * 显示查询商品页面
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("item/list")
	@ResponseBody
	public EasyUIDataGrid getItemList(int page,int rows) {
		//返回分页后形成的easyUIDataGrid对象
		return tbItemServiceImpl.getTbItemList(page, rows);
	}
	
	/**
	 * 显示商品修改的界面
	 * @return
	 */
	@RequestMapping("rest/page/item-edit")
	public String showEdit() {
		return "item-edit";
	}
	
	/**
	 * 商品删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/rest/item/delete")
	@ResponseBody
	public Result delTbItem(String ids) {
		 return  tbItemServiceImpl.updTbItem(ids, (byte)3);
	}
	
	/**
	 * 商品下架
	 * @param ids
	 * @return
	 */
	@RequestMapping("/rest/item/instock")
	@ResponseBody
	public Result instockTbItem(String ids) {
		return tbItemServiceImpl.updTbItem(ids, (byte)2);
	}
	
	/**
	 * 商品上架
	 * @param ids
	 * @return
	 */
	@RequestMapping("/rest/item/reshelf")
	@ResponseBody
	public Result reshelfTbItem(String ids) {
		return tbItemServiceImpl.updTbItem(ids, (byte)1);
	}
	
	@RequestMapping("/item/save")
	@ResponseBody
	public Result saveItemAndDesc(TbItem item,String desc,String itemParams) {
		int index = 0;
		Result result = new Result();
		try {
			index = tbItemServiceImpl.insTbItemAndDesc(item, desc,itemParams);
			if(index==1){
				result.setStatus(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setData(e.getMessage());
		}
		return result;
	}
	
}
