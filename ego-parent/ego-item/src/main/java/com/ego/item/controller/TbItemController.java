package com.ego.item.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ego.item.service.TbItemService;

@Controller
public class TbItemController {
	@Resource
	private TbItemService tbItemServiceImpl;
	
	/**
	 * 显示商品详情,使用作用域进行传值
	 * @param id
	 * @return
	 */
	@RequestMapping("/item/{id}.html")
	public String showItemDetails(@PathVariable long id,Model model) {
		tbItemServiceImpl.showDetails(id);
		model.addAttribute("item",tbItemServiceImpl.showDetails(id));
		return "item";
	}
}
