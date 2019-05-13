package com.ssm.manager.controller;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.commons.pojo.TreeNode;
import com.ssm.manager.service.TbItemCatService;

@Controller
public class TbItemCatController {
	@Resource
	private TbItemCatService tbItemCatServiceImpl;
	
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<TreeNode> selCatList(@RequestParam(value="id",defaultValue="0") long id) {
		return tbItemCatServiceImpl.selCatList(id);
	}
}
