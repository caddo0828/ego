package com.ssm.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 显示页面的控制器
 * @author 老腰
 *
 */
@Controller
public class PageController {
	@RequestMapping("/")
	public String showIndex() {
		return "index";
	}
	
	/**
	 * 跳转到其他页面（还没有写其他控制器功能的时候先显示出来其他界面）
	 * @param path
	 * @return
	 */
	@RequestMapping("{page}")
	public String page(@PathVariable String page) {
		return page;
	}
}
