package com.ego.item.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.common.pojo.TbItemChild;
import com.ego.common.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.item.service.TbItemService;
import com.ego.pojo.TbItem;
import com.ego.redis.dao.JedisDao;

@Service
public class TbItemServiceImpl implements TbItemService{
	@Reference
	private TbItemDubboService tbItemDubboServiceImpl;
	@Resource
	private JedisDao jedisDaoImpl;
	@Value("${redis.item.key}")
	private String itemKey;
	
	/**
	 * 将商品详情数据添加到redis缓存中，增加响应速率,并且保证每次操作同一个key，将key属性添加到属性文件中
	 */
	@Override
	public TbItemChild showDetails(long id) {
		/*先判断redis中是否存在对应的键，并且数据不为空，不为空，取出对应的数据
		  避免redis中存入数据，每次存的对象都被上一个对象覆盖，键key+id
		*/
		String key = itemKey+id;
		if(jedisDaoImpl.exists(key)) {
			String value = jedisDaoImpl.get(key);
			if(value!=null&&!value.equals("")) {
				//从redis中取出来的是普通的字符串，前台要的是对象（键值对数据），因此将字符串转换成对象
				return JsonUtils.jsonToPojo(value, TbItemChild.class);
			}
		}
		
		TbItem tbItem = tbItemDubboServiceImpl.selById(id);
		TbItemChild child = new TbItemChild();
		child.setId(tbItem.getId());
		child.setPrice(tbItem.getPrice());
		child.setSellPoint(tbItem.getSellPoint());
		child.setTitle(tbItem.getTitle());
		//前台防止图片数据索引越界，当图片数组为空时，创建一个数组，默认值为“”
		child.setImages(tbItem.getImage()==null||tbItem.getImage().equals("")? new String[1] : tbItem.getImage().split(",") );
		//将数据添加到缓存中
		jedisDaoImpl.set(key, JsonUtils.objectToJson(child));
		return child;
	}

}
