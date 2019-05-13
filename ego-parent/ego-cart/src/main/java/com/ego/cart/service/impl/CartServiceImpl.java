package com.ego.cart.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.cart.service.CartService;
import com.ego.common.pojo.Result;
import com.ego.common.pojo.TbItemChild;
import com.ego.common.utils.CookieUtils;
import com.ego.common.utils.HttpClientUtil;
import com.ego.common.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.redis.dao.JedisDao;

@Service
public class CartServiceImpl implements CartService{
	@Reference
	private TbItemDubboService tbItemDubboServiceImpl;
	@Resource
	private JedisDao jedisDaoImpl;
	@Value("${passport.url}")
	private String url;
	@Value("${cart.key}")
	private String cartKey;
	
	/**
	 * 业务逻辑，判断redis中是否存在商品数据
	 * 	1、存在：
	 * 		取出数据，判断是否存在相同的数据，相同则数量加
	 * 		       不存在：重新将商品添加到集合中
	 *  2、不存在：
	 *  	直接new新的集合对象，添加商品
	 * 		
	 */
	@Override
	public void addCart(long id, int num,HttpServletRequest request) {
		//由于每个用户的cookie信息唯一，取出key查找用户信息
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		//请求获取用户的信息,HttpClient请求,获取响应体信息
		String entity = HttpClientUtil.doPost(url+token);
		//原本响应返回的是一个Result对象
		Result result = JsonUtils.jsonToPojo(entity, Result.class);
		//jackson在将josn转成对象的过程中，如果对象属性中包含了其他的对象，会把其他对象的转成LinkedHashMap
		//redis中的key
		String key = cartKey+((LinkedHashMap)result.getData()).get("username");
		
		//保证可以存放多个商品信息，操作同一个key,存放的就是一个集合，而且判断的是否已经存在，已经存在数量加1
		List<TbItemChild> list = new ArrayList<>();
		
		//判断redis中是否存在key
		if(jedisDaoImpl.exists(key)) {
			//代表用户已经添加数据到redis中，取出集合数据判断商品是否已经存在，存在数量加
			//否则从数据库获取数据，加入集合
			String value = jedisDaoImpl.get(key);
			//键存在，数值不存在的情况
			if(value!=null&&!value.equals("")) {
				list = JsonUtils.jsonToList(value, TbItemChild.class);
				for (TbItemChild tbItemChild : list) {
					if((Long)tbItemChild.getId()==id) {
						//存在key ，并且数据存在于集合中
						tbItemChild.setNum(tbItemChild.getNum()+num);
						jedisDaoImpl.set(key, JsonUtils.objectToJson(list));
						return ;
					}
				}
			}
		}
		//1、不存在key, 2、存在key，但是数据不存在
		//数据库查询商品信息
		TbItem tbItem = tbItemDubboServiceImpl.selById(id);
		
		//最终返回给前台一个TbItemChild对象
		TbItemChild child = new TbItemChild();
		child.setId(tbItem.getId());
		child.setTitle(tbItem.getTitle());
		child.setImages(tbItem.getImage()==null||tbItem.getImage().equals("")? new String[1] : tbItem.getImage().split(","));
		child.setNum(num);
		child.setPrice(tbItem.getPrice());
		//将数据添加进入集合中
		list.add(child);
		//数据加入缓存
		jedisDaoImpl.set(key, JsonUtils.objectToJson(list));

	}

	@Override
	public List<TbItemChild> showCart(HttpServletRequest request) {
		//从cookie获取redis的key
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		//响应体信息
		String entity = HttpClientUtil.doPost(url+token);
		//获取响应体中用户信息
		Result result = JsonUtils.jsonToPojo(entity, Result.class);
		//redis的键值为 cartKey+username的形式
		String key = cartKey+((LinkedHashMap)result.getData()).get("username");
		//取出购物商品信息
		String redisList = jedisDaoImpl.get(key);
		//避免用户购物车数据为空时，空指针异常
		if(redisList!=null&&!redisList.equals("")) {
			return JsonUtils.jsonToList(redisList, TbItemChild.class);
		}
		return null;
	}

	@Override
	public Result delCart(long id ,HttpServletRequest request) {
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String entity = HttpClientUtil.doPost(url+token);
		Result result = JsonUtils.jsonToPojo(entity, Result.class);
		String key = cartKey+((LinkedHashMap)result.getData()).get("username");
		String redisList = jedisDaoImpl.get(key);
		List<TbItemChild> list = JsonUtils.jsonToList(redisList, TbItemChild.class);
		
		TbItemChild child = null;
		for (TbItemChild tbItemChild : list) {
			if((Long)tbItemChild.getId()==id) {
				//集合在遍历过程不允许删除集合元素，只能使用外部变量接收，遍历的外部删除
				//list.remove(tbItemChild);  会报异常，使用迭代器遍历才可以从集合中移除数据
				//踩了坑的地方，赋值赋反了
				child = tbItemChild;
			}
		}
		//删除对应的list数据
		list.remove(child);
		//重新添加到集合中，数据加入缓存, 缓存操作成功后，返回结果为“OK”
		String res = jedisDaoImpl.set(key, JsonUtils.objectToJson(list));
		Result delResult = new Result();
		if(res.equals("OK")) {
			delResult.setStatus(200);
		}
		return delResult;
	}

	@Override
	public Result updateCart(long id, int num, HttpServletRequest request) {
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String entity = HttpClientUtil.doPost(url+token);
		Result result = JsonUtils.jsonToPojo(entity, Result.class);
		String key = cartKey+((LinkedHashMap)result.getData()).get("username");
		
		String redisList = jedisDaoImpl.get(key);
		List<TbItemChild> list = JsonUtils.jsonToList(redisList, TbItemChild.class);
		for (TbItemChild tbItemChild : list) {
			if((Long)tbItemChild.getId()==id) {
				tbItemChild.setNum(num);
			}
		}
		
		//重新将数据添加到缓存中
		String res = jedisDaoImpl.set(key, JsonUtils.objectToJson(list));
		Result er = new Result();
		if(res.equals("OK")) {
			//redis缓存操作成功
			er.setStatus(200);
		}
		return er;
	}

}
