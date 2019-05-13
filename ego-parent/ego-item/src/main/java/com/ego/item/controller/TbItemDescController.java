package com.ego.item.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.item.service.TbItemDescService;

@Controller
public class TbItemDescController {
	@Resource
	private TbItemDescService tbItemDescServiceImpl;
	
	/**
	 * 查询商品描述信息，使用伪静态的ajax请求方式
	 * 使用requestbody返回ajax请求数据，返回数据是单纯的字符串时，容易产生中文乱码
	 * @return
	 */
	@RequestMapping(value="/item/desc/{id}.html",produces="text/html;charset=utf-8")
	@ResponseBody
	public String showDesc(@PathVariable long id) {
		return tbItemDescServiceImpl.selDescById(id);
	}
	
	
}
