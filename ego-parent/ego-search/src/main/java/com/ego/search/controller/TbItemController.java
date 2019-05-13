package com.ego.search.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ego.pojo.TbItem;
import com.ego.search.service.TbItemService;

@Controller
public class TbItemController {
	@Resource
	private TbItemService tbItemServiceImpl;
	
	
	/**responsebody中返回字符串的数据时，默认只有text/html属性，要使用produces防止中文乱码
	 * 将数据初始化保存在solr中
	 * @return
	 */
	@RequestMapping(value="solr/init",produces="text/html;charset=utf-8")
	@ResponseBody
	public String init() {
		long start = System.currentTimeMillis();
		try {
			tbItemServiceImpl.init();
			long end = System.currentTimeMillis();
			return "初始化总时间"+(end-start)/1000+"秒";
		} catch (Exception e){
			e.printStackTrace();
			return "初始化失败";
		}
	}
	
	//伪静态:将控制器伪装成静态文件，Ajax不允许请求控制器名为.html的控制器
	/**
	 * 搜索功能
	 * @param model
	 * @param q
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("search.html")
	public String search(Model model,String q ,@RequestParam(defaultValue="1") int page ,@RequestParam(defaultValue="12") int rows){
		//get请求的数据不能走springmvc的过滤器，因此会乱码,将字符串先打散，在组合
		try {
			q = new String(q.getBytes("ISO-8859-1"),"utf-8");
			Map<String, Object> map = tbItemServiceImpl.selByQuery(q, page, rows);
			//前台要的是存储在作用域中的值
			model.addAttribute("query", q);
			model.addAttribute("itemList", map.get("itemList"));
			model.addAttribute("totalPages", map.get("totalPages"));
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//跳转到查询的页面
		return "search";
	}
	
	/**
	 * 将后台添加到数据库中的新的商品的数据。添加一份到solr服务器中
	 * 返回1代表添加成功
	 * @RequestBody 代表，将请求体中的流数据(json)转换成对象，前提传入的是一个流数据
	 * @param item
	 * @return
	 */
	@RequestMapping("solr/add")
	@ResponseBody
	public int addSolr(@RequestBody TbItem item) {
		try {
			return tbItemServiceImpl.addSolr(item);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return 0;
	}
	
	/*@RequestMapping("solr/add")
	@ResponseBody
	public int addSolr(@RequestBody Map<String,Object> map) {
		return tbItemServiceImpl.addSolr((LinkedHashMap)map.get("item"),map.get("desc").toString());
	}
	*/
}
