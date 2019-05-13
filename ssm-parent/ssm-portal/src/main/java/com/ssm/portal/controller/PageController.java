package com.ssm.portal.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssm.portal.service.TbContentService;

@Controller
public class PageController {
	@Resource
	private TbContentService tbContentServiceImpl;
	
	@RequestMapping("/")
	public String showPage(Model model) {
		model.addAttribute("ad1", tbContentServiceImpl.picContent());
		return "index";
	}
	
	
		
}
