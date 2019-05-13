package com.ego.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	/**
	 * 展示portal界面的首页
	 * @return
	 */
	@RequestMapping("/")
	public String welcome() {
		return "forward:showBigPic";
	}
}
