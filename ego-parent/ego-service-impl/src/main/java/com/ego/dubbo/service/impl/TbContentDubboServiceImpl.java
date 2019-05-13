package com.ego.dubbo.service.impl;

import java.util.List;
import javax.annotation.Resource;
import com.ego.common.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.mapper.TbContentMapper;
import com.ego.pojo.TbContent;
import com.ego.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TbContentDubboServiceImpl implements TbContentDubboService{
	@Resource
	private TbContentMapper tbContentMapper;
	
	@Override
	public EasyUIDataGrid selContentList(long categoryId, int page, int rows) {
		//设置分页条件
		PageHelper.startPage(page, rows);
		TbContentExample example = new TbContentExample();
		//当内容id为0时，默认查询所有内容，传递过来id表示查询当前分类内容下的所有内容信息
		if(categoryId!=0) {
			example.createCriteria().andCategoryIdEqualTo(categoryId);
		}
		List<TbContent> list = tbContentMapper.selectByExample(example);
		PageInfo<TbContent> pi = new PageInfo<>(list);
		EasyUIDataGrid dataGrid = new EasyUIDataGrid();
		dataGrid.setTotal(pi.getTotal());
		dataGrid.setRows(pi.getList());
		return dataGrid;
	}

	@Override
	public int delByIds(long id) throws Exception {
		//考虑事务回滚
		int index = 0;
		try {
			index =tbContentMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(index==1) {
			return 1;
		}else {
			throw new Exception("内容删除失败，数据回滚");
		}
	}

	@Override
	public int updContent(TbContent content) throws Exception {
		int index = 0;
		try {
			index = tbContentMapper.updateByPrimaryKeySelective(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(index==1) {
			return 1;
		}else {
			throw new Exception("内容修改失败，数据还原");
		}
	}

	@Override
	public int insContent(TbContent content) throws Exception {
		int index = 0;
		try {
			index = tbContentMapper.insertSelective(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(index==1) {
			return 1;
		}else {
			throw new Exception("新增内容失败");
		}
	}

	@Override
	public List<TbContent> selByCount(int count, boolean isSort) {
		//判断是否按照排序进行大广告位查询
		TbContentExample example = new TbContentExample();
		if(isSort) {
			//按照更新时间进行降序排序: order by updated desc; 
			example.setOrderByClause("updated desc");
		}
		//展示前几条数据，分页处理
		if(count!=0) {
			PageHelper.startPage(1, count);
			List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
			//封装分页查询结果
			PageInfo<TbContent> pi = new PageInfo<>(list);
			return pi.getList();
		}else {
			//查询大广告中的全部完整数据
			return tbContentMapper.selectByExampleWithBLOBs(example);
		}
	}
	 
}
