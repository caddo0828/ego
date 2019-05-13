package com.ego.search.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.common.pojo.TbItemChild;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemDesc;
import com.ego.search.service.TbItemService;

@Service
public class TbItemServiceImpl implements TbItemService{
	@Reference
	private TbItemDubboService tbItemDubboServiceImpl;
	@Reference
	private TbItemCatDubboService tbItemCatDubboServiceImpl;
	@Reference
	private TbItemDescDubboService tbItemDescDubboServiceImpl;
	@Resource
	private CloudSolrClient solrClient; //将solr集群对象交由spring管理
	
	@Override
	public void init() throws SolrServerException,IOException {
		//查询所有的状态正常的商品
		List<TbItem> listItem = tbItemDubboServiceImpl.selAllByStatu((byte)1);
		for (TbItem tbItem : listItem) {
			//查询商品类目信息
			TbItemCat cat = tbItemCatDubboServiceImpl.selByCatId(tbItem.getCid());
			//查询商品描述
			TbItemDesc desc = tbItemDescDubboServiceImpl.selByItemId(tbItem.getId());
		    //创建solr的document对象
			SolrInputDocument doc = new SolrInputDocument();
			//往document对象中添加属性字段,属性对象中id一定不能少
			//addFiled表示当属性不存在的时候，添加
			doc.addField("id", tbItem.getId());
			doc.addField("item_title", tbItem.getTitle());
			doc.addField("item_sell_point", tbItem.getSellPoint());
			doc.addField("item_price", tbItem.getPrice());
			doc.addField("item_image", tbItem.getImage());
			doc.addField("item_category_name", cat.getName());
			doc.addField("item_desc", desc.getItemDesc());
			//添加排序字段，上solr字段中添加schemea,重新初始化数据的时候要将全部数据先进行删除
			//doc.addFiled("",tbItem.getUpdated);
			
			//创建solr的document对象
			solrClient.add(doc);
		}
		//solr中需要进行事务的提交
		solrClient.commit();
	}

	@Override
	public Map<String, Object> selByQuery(String query, int page, int rows) throws SolrServerException, IOException {
		//可视化左侧查询条件对象
		SolrQuery params = new SolrQuery();
		//设置查询条件
		params.setQuery("item_keywords:"+query);
		//设置不拆分查询
		//params.setQuery("item_keywords:\""+query+"\"");
		
		//设置分页条件
		//从第几条开始查询 start= 每页显示多少条/（页数-1）
		params.setStart(rows*(page-1));
		//查询几个数据
		params.setRows(rows);
		//设置排序,降序排序
		//params.setSort("item_updated", ORDER.desc);
		
		//设置高亮
		params.setHighlight(true);
		//设置高亮属性,只对标题进行高亮
		params.addHighlightField("item_title");
		//设置高亮前缀
		params.setHighlightSimplePre("<span style='color:red'>");
		//设置高亮后缀
		params.setHighlightSimplePost("</span>");
		
		//设置为post方式查询，防止中文乱码
		//返回全部查询数据responseHeader,response,hightting
		QueryResponse response = solrClient.query(params);
		//查询（未高亮内容）response对象
		SolrDocumentList docList = response.getResults();
		//查询出高亮部分hightting
		Map<String, Map<String, List<String>>> high = response.getHighlighting();
		
		//返回数据map集合中要存放 泛型为TbItemChild的泛型的list集合
		List<TbItemChild> itemChilds = new ArrayList<>();
		
		//遍历docs集合，得到每一个doc对象
		for (SolrDocument document : docList) {
			TbItemChild child = new TbItemChild();
			child.setId(Long.parseLong((String)document.getFieldValue("id")));
			//获取高亮的title部分的集合对象，有可能存在为空的属性值
			List<String> hightList = high.get(document.getFieldValue("id")).get("item_title");
			if(hightList!=null&&hightList.size()>0) {
				//存在高亮
				child.setTitle(hightList.get(0));
			}else {
				//没有高亮
				child.setTitle(document.getFieldValue("item_title").toString());
			}
			child.setPrice((Long)document.getFieldValue("item_price"));
			//图片可能存在的情况为一张，多张，或者没有,前台对图片数组进行了索引获取，应该先确保图片数组不为空
			Object image = document.getFieldValue("item_image");
			//判断是否为空或值为空。将多个图片对象进行逗号拆分变成数组
			child.setImages(image==null||image.equals("")? new String[1]:image.toString().split(","));
			child.setSellPoint(document.getFieldValue("item_sell_point").toString());
	
			itemChilds.add(child);	
		}
		
		Map<String, Object> resultMap = new HashMap<>();
		//获取到的是一个查询出的集合对象
		resultMap.put("itemList", itemChilds);
		//总页数=总数据/每页显示多少条数据
		resultMap.put("totalPages", docList.getNumFound()%rows==0 ? docList.getNumFound()/rows : (docList.getNumFound()/rows)+1);
		/*//查询的数据是哪个
		resultMap.put("query", query);
		//第几页
		resultMap.put("page", page);*/
		
		return resultMap;
	}

	@Override
	public int addSolr(TbItem item) throws SolrServerException, IOException {
		SolrInputDocument doc = new SolrInputDocument();
		//setFiled：表示恒加数据
		doc.setField("id", item.getId());
		doc.setField("item_title", item.getTitle());
		doc.setField("item_sell_point", item.getSellPoint());
		doc.setField("item_price", item.getPrice());
		doc.setField("item_image", item.getImage());
		doc.setField("item_category_name", tbItemCatDubboServiceImpl.selByCatId(item.getCid()).getName());
		doc.setField("item_desc",tbItemDescDubboServiceImpl.selByItemId(item.getId()).getItemDesc() );
		
		UpdateResponse response = solrClient.add(doc);
		//提交事务
		solrClient.commit();
		//提交后的响应头中的响应编码为0，代表solr数据添加成功
		if(response.getStatus()==0) {
			return 1;
		}
		return 0;
	}

	
}
