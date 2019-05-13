package com.ssm.dubbo.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssm.commons.pojo.EasyUIDataGrid;
import com.ssm.dubbo.service.TbContentDubboService;
import com.ssm.mapper.TbContentMapper;
import com.ssm.pojo.TbContent;
import com.ssm.pojo.TbContentExample;

public class TbContentDubboServiceImpl implements  TbContentDubboService{
	@Resource
	private TbContentMapper tbContentMapper;
	
	@Override
	public EasyUIDataGrid selContentList(long categoryId, int page, int rows) {
		//先设置分页条件
		PageHelper.startPage(page, rows);
		
		List<TbContent> list = new ArrayList<>();
		
		//查询出对应条件的集合，获取SQL语句
		TbContentExample example = new TbContentExample();
		//当categoryId=0时，默认查询全部数据
		if(categoryId==0) {
			list = tbContentMapper.selectByExampleWithBLOBs(example);
		}else {
			example.createCriteria().andCategoryIdEqualTo(categoryId);
			list = tbContentMapper.selectByExampleWithBLOBs(example);
		}
	
		//封装成PageInfo对象
		PageInfo<TbContent> pi = new PageInfo<>(list);
		EasyUIDataGrid dataGrid = new EasyUIDataGrid();
		dataGrid.setTotal(pi.getTotal());
		dataGrid.setRows(pi.getList());
		return dataGrid;
	}

	@Override
	public int updContent(TbContent content) throws Exception {
		int index  = tbContentMapper.updateByPrimaryKeySelective(content);
		if(index>0) {
			return 1;
		}else {
			throw new Exception("修改内容失败");
		}
	}

	@Override
	public int delContents(long id) throws Exception {
		int index  = tbContentMapper.deleteByPrimaryKey(id);
		if(index > 0) {
			return 1;
		}else {
			throw new Exception("删除内容数据失败");
		}
	}

	@Override
	public int insContent(TbContent content) throws Exception {
		int index = tbContentMapper.insertSelective(content);
		if(index>0) {
			return 1;
		}else {
			throw new Exception("新增内容失败");
		}
	}

	@Override
	public List<TbContent> selPicList(int count, boolean sort) {
		TbContentExample example = new TbContentExample();
		if(sort) {
			//如果要进行排序显示,按照更新时间进行降序排序
			example.setOrderByClause("updated desc");
		}
		
		if(count!=0) {
			//代表查询出几个数据,分页查询
			PageHelper.startPage(1, count);
			List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
			PageInfo<TbContent> pi = new PageInfo<>(list);
			return pi.getList();
		}else {
			//没有设置查询几条数据的话，就查询全部
			return tbContentMapper.selectByExampleWithBLOBs(example);
		}
		
	}

}
