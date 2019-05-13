package com.ego.item.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.common.utils.JsonUtils;
import com.ego.dubbo.service.TbItemParamItemDubboService;
import com.ego.item.pojo.ParamItem;
import com.ego.item.service.TbItemParamItemService;
import com.ego.pojo.TbItemParamItem;
import com.ego.redis.dao.JedisDao;

@Service
public class TbItemParamItemServiceImpl implements TbItemParamItemService{
	//调用dubbo服务
	@Reference
	private TbItemParamItemDubboService tbItemParamItemDubboServiceImpl;
	@Resource
	private JedisDao jedisDaoImpl;
	@Value("${redis.item.param.item.key}")
	private String paramKey;
	
	
	//[{"group":"主体","params":[{"k":"品牌","v":"联想(Lenovo)"},{"k":"型号","v":"酷睿i5"}]},{"group":"参数","params":[{"k":"支持语言","v":"中文，英语"},{"k":"产品内存","v":"8G，1000GB"}]}]
	@Override
	public String selParamByItemId(long itemId) {
		//将规格参数存储到redis中,先查再判断
		String key = paramKey+itemId;
		if(jedisDaoImpl.exists(key)) {
			//取出数据
			String value = jedisDaoImpl.get(key);
			if(value!=null&&!value.equals("")) {
				return value;
			}
		}
		
		//否则缓存中没有数据就在数据库中先查在添加进入缓存
		
		//规格参数对象，如果直接调getParamData,就相当于一个空的对象去调方法会抛空指针异常
		TbItemParamItem tbItemParamItem = tbItemParamItemDubboServiceImpl.selByItemId(itemId);
		 
		//使用字符串拼接对象拼接数据，每一个大的参数规格为一个表格对象
		StringBuffer buffer = new StringBuffer();
		if(tbItemParamItem!=null) {
			//将json数据转换成list集合，泛型使用
			List<ParamItem> paramList = JsonUtils.jsonToList(tbItemParamItem.getParamData(), ParamItem.class);
			//[{"group":"主体","params":[{"k":"品牌","v":"联想(Lenovo)"},{"k":"型号","v":"酷睿i5"}]},{"group":"参数","params":[{"k":"支持语言","v":"中文，英语"},{"k":"产品内存","v":"8G，1000GB"}]}]
			/**返回的是一个数组，也就是对象或者集合
			 * group普通字符串，param： 集合 ，泛型放对象
			 * 泛型对象里在放对象。 对象包含k , v 属性
			 * 将普通的json数据转成list集合
			 */
			for (ParamItem paramItem : paramList) {
				//拼接表格
				buffer.append("<table width='500' style='color:gray;'>");
			    //遍历每一个paramItem的params属性得到具体的K，V，第一个td中包含
				for(int i=0;i<paramItem.getParams().size();i++) {
					if(i==0) {
						//主体部分,起始部分包含一个空的td
						buffer.append("<tr>");
						buffer.append("<td align='right';width='30%'>"+paramItem.getGroup()+"</td>");
						buffer.append("<td align='right';width='30%'>"+paramItem.getParams().get(i).getK()+"</td>");
						buffer.append("<td align='right';width='30%'>"+paramItem.getParams().get(i).getV()+"</td>");
						buffer.append("</tr>");
					}else {
						buffer.append("<tr>");
						buffer.append("<td></td>");
						buffer.append("<td align='right'>"+paramItem.getParams().get(i).getK()+"</td>");
						buffer.append("<td align='right'>"+paramItem.getParams().get(i).getV()+"</td>");
						buffer.append("</tr>");					
				    
					}
					
				}
				buffer.append("</table>");
				buffer.append("<hr style='color:gray;'/>");
			}
			
		}
		 //将数据添加入缓存
		 jedisDaoImpl.set(key, buffer.toString());
		 
		 return buffer.toString();
	}

}
