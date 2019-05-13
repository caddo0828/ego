package com.ego.portal.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import com.ego.portal.service.TbContentService;

@Controller
public class TbContentController {
	@Resource
	private TbContentService tbContentServiceImpl;
	
	/**
	 * 显示大广告位的图片信息，使用作用域传值
	 * @return
	 */
	@RequestMapping("showBigPic")
	public String showPic(Model model){
		//传递作用域中的值
		model.addAttribute("ad1", tbContentServiceImpl.selByCount());
		return "index";
	}
	
	
}
