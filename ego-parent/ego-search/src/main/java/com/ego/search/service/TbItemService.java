package com.ego.search.service;

import java.io.IOException;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;

import com.ego.pojo.TbItem;

public interface TbItemService {
	/**solr数据初始化,也就是将数据库的数据添加进solr中
	 * @throws SolrServerException
	 * @throws IOException
	 */
	void init()throws SolrServerException, IOException ;
	
	/**
	 * 根据输入的数据进行分页查询
	 * 前台要求进行分页显示结果，并且结果中包含基本数据类型和对象类型，又是键值对的方式
	 * 并且键值对中包含了list集合，list集合里存放的是自定义对象。键值对中还包含了基本数据类型的数据
	 * 因此使用map集合，可以存放list，也可以存放基本类型
	 * @param query
	 * @return
	 */
	Map<String, Object> selByQuery(String query,int page,int rows)throws SolrServerException, IOException;
	
	/**
	 * 将后台新增的数据添加到solr中进行同步操作
	 * @param item
	 * @return
	 */
	int addSolr(TbItem item) throws SolrServerException, IOException;
}
