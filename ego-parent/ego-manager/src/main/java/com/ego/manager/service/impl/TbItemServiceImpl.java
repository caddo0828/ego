package com.ego.manager.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.common.pojo.EasyUIDataGrid;
import com.ego.common.pojo.Result;
import com.ego.common.utils.HttpClientUtil;
import com.ego.common.utils.IDUtils;
import com.ego.common.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.manager.service.TbItemService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;
import com.ego.redis.dao.JedisDao;

//此处注意一定要走的是Spring的service注解，不然容易起冲突
@Service
public class TbItemServiceImpl implements TbItemService{
	@Reference
	private TbItemDubboService tbItemDubooServiceImpl;
	@Value("${search.url}")
	private String url ;
	@Resource
	private JedisDao jedisDaoImpl;
	@Value("${redis.item.key}")
	private String itemKey;
	
	
	@Override
	public EasyUIDataGrid getTbItemList(int page, int rows) {
		return tbItemDubooServiceImpl.getTbItemList(page, rows);
	}

	@Override
	public Result updTbItem(String ids, byte status) {
		TbItem item = new TbItem();
		int index = 0;
		//前台接收的数据是一个以逗号进行分隔的字符串
		String[] idsArray = ids.split(",");
		for (String id : idsArray) {
			item.setId(Long.parseLong(id));
			item.setStatus(status);
			try {
				index += tbItemDubooServiceImpl.updTbItem(item);
				
				//判断商品是正常，还是下架，还是删除
				if(status==2||status==3) {
					//表示下架或者删除，则从redis中移除数据,批量删除
					jedisDaoImpl.del(itemKey+id);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Result result = new Result();
		if(index==idsArray.length) {
			result.setStatus(200);
			return result;
		}
		return result;
	}

	@Override
	public int insTbItemAndDesc(TbItem item, String desc,String itemParams) throws Exception {
		//使用dubbo中带有事务回滚的方法
		long id = IDUtils.getItemId();
		Date date = new Date();
		item.setId(id);
		//新增商品状态应该属于正常
		item.setStatus((byte)1);
		item.setCreated(date);
		item.setUpdated(date);
		//商品描述对象
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(id);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		//商品具体参数
		TbItemParamItem paramItem = new TbItemParamItem();
		paramItem.setCreated(date);
		paramItem.setUpdated(date);
		paramItem.setParamData(itemParams);
		paramItem.setItemId(id);
		int index = 0;
		try {
			index = tbItemDubooServiceImpl.insTbItemAndDesc(item, itemDesc,paramItem);
			
			//方式一、
			/*//普通方式进行传递参数值, 模拟浏览器传递的值，并且将值注入到控制器方法参数中
			Map<String,String> map = new HashMap<>();
			//因为其他项目要进行设值注入，
			map.put("id",item.getId().toString());
			map.put("title", item.getTitle());
			map.put("sellPoint", item.getSellPoint());
			map.put("price",item.getPrice().toString());
			map.put("image", item.getImage());
			map.put("desc",desc);  //此时 其他项目中的控制器接收的参数 加上String desc
			//使用java代码调用其他项目的控制器
			HttpClientUtil.doPost(url, map);*/
			
			//方式二、参数内容体转换为json流数据传递
			/**
			 *此处中不被事务包围，可以直接从数据库中获取到刚刚插入成功的desc对象的值，以及cata对象
			 *如果此处有事务，就必须从当前把desc数据传递过去，而不能通过dubbo进行查询了，因为此时dubbo中事务还未提交
			 */
			new Thread() {
				public void run() {
					HttpClientUtil.doPostJson(url, JsonUtils.objectToJson(item));
				};
			}.start();
			
		//方式三、有事务的处理方式,使用线程，线程要求对象变量只能是final类型的，主要为了避免空
		/*final TbItem itemFinal = item;
		final String descFinal = desc;
		new Thread(){
			public void run() {
				Map<String,Object> map = new HashMap<>();
				map.put("item", itemFinal);
				map.put("desc", descFinal);
				
				HttpClientUtil.doPostJson(url, JsonUtils.objectToJson(map));
				//使用java代码调用其他项目的控制器
			};
		}.start();	*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return index;
	}

}
