package com.ego.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.common.pojo.Result;
import com.ego.common.pojo.TbItemChild;
import com.ego.common.utils.CookieUtils;
import com.ego.common.utils.HttpClientUtil;
import com.ego.common.utils.IDUtils;
import com.ego.common.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.order.pojo.OrderParam;
import com.ego.order.service.OrderService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;
import com.ego.redis.dao.JedisDao;

@Service
public class OrderServiceImpl implements OrderService{
	@Resource
	private JedisDao jedisDaoImpl;
	@Value("${cart.key}")
	private String cartKey;
	@Value("${passport.url}")
	private String url;
	@Reference
	private TbItemDubboService tbItemDubboServiceImpl;
	@Reference
	private TbOrderDubboService tbOrderDubboServiceImpl;
	
	
	//redis的key组成为：  cartKey+username
	@Override
	public List<TbItemChild> showOrderCart(List<Long> ids, HttpServletRequest request) {
		List<TbItemChild> orderList = new ArrayList<>();
		//取出redis数据，先取cookie在取用户名，组成redis的key
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String entity = HttpClientUtil.doPost(url+token);
		Result result = JsonUtils.jsonToPojo(entity, Result.class);
		String key = cartKey+((LinkedHashMap)result.getData()).get("username");
		
		String value = jedisDaoImpl.get(key);
		//键存在，且值存在
		if(value!=null&&!value.equals("")) {
			List<TbItemChild> list = JsonUtils.jsonToList(value, TbItemChild.class);
			for (TbItemChild tbItemChild : list) {
				for (long id : ids) {
					//双等比较地址，将两个long同时转成基本类型,就比较内容了
					if((long)tbItemChild.getId()==(long)id) {
						//添加前先进行库存量判断
						TbItem tbItem = tbItemDubboServiceImpl.selById(id);
						if(tbItem.getNum()>=tbItemChild.getNum()) {
							tbItemChild.setEnough(true);
						}else {
							tbItemChild.setEnough(false);
						}
						orderList.add(tbItemChild);
					}
				}
			}
		}
		return orderList;
	}

	@Override
	public Result createOrder(OrderParam param, HttpServletRequest request) {
		String cookieValue = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String entity = HttpClientUtil.doPost(url+cookieValue);
		Result result = JsonUtils.jsonToPojo(entity, Result.class);
		//json中转换出来的data属性为object,不知道具体类型，就直接转成了LinkedHashMap
		Map map =((LinkedHashMap)result.getData());
	    //jedis中存的key
		String key = cartKey+map.get("username");
		
		//订单表
		TbOrder order = new TbOrder();
		long orderId = IDUtils.getItemId();
		order.setOrderId(orderId+"");
		//实付金额
		order.setPayment(param.getPayment());
		//支付类型
		order.setPaymentType(param.getPaymentType());
		Date date = new Date();
		order.setCreateTime(date);
		order.setUpdateTime(date);
		//买家是否评价
		order.setBuyerRate(0);
		//用户id
		order.setUserId(Long.parseLong(map.get("id").toString()));
		//用户昵称
		order.setBuyerNick(map.get("username").toString());
		
		//订单-商品表
		for(TbOrderItem tbOrderItem : param.getOrderItems()) {
			tbOrderItem.setId(IDUtils.getItemId()+"");
			tbOrderItem.setOrderId(orderId+"");
		}
		
		//收货信息表
		TbOrderShipping orderShipping = param.getOrderShipping();
		orderShipping.setOrderId(orderId+"");
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		
		Result er = new Result();
		try {
			int index = tbOrderDubboServiceImpl.insOrderParam(order, param.getOrderItems(), orderShipping);
			if(index>0) {
				//所有数据新增成功
				er.setStatus(200);
				
				//删除购买的商品
				String value = jedisDaoImpl.get(key);
				List<TbItemChild> jsonList = JsonUtils.jsonToList(value, TbItemChild.class);
				List<TbItemChild> deleList = new ArrayList<>();
				if(jsonList!=null&&jsonList.size()>0) {
					for (TbItemChild tbItemChild : jsonList) {
						for (TbOrderItem orderItem : param.getOrderItems()) {
							//将Long包装类转换成基本数据类型。比较内容
							if(tbItemChild.getId().longValue()==Long.parseLong(orderItem.getItemId())) {
								//将要进行删除的商品进行添加
								deleList.add(tbItemChild);
							}
						}
					}	
				}
				
				for (TbItemChild tbItemChild : deleList) {
					jsonList.remove(tbItemChild);
				}
				
				//重新将数据添加到redis缓存中
				jedisDaoImpl.set(key, JsonUtils.objectToJson(jsonList));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return er;
	}

}
