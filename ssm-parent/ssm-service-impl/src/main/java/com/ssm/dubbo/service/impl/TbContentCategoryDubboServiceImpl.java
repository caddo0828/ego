package com.ssm.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ssm.dubbo.service.TbContentCategoryDubboService;
import com.ssm.mapper.TbContentCategoryMapper;
import com.ssm.pojo.TbContentCategory;
import com.ssm.pojo.TbContentCategoryExample;

public class TbContentCategoryDubboServiceImpl implements TbContentCategoryDubboService{
	@Resource
	private TbContentCategoryMapper tbContentCategoryMapper;
	
	@Override
	public List<TbContentCategory> selCatList(long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		//只查询状态正常的节点
		example.createCriteria().andParentIdEqualTo(parentId).andStatusEqualTo(1);
		return tbContentCategoryMapper.selectByExample(example);
	}

	@Override
	public int insContentCat(TbContentCategory category) throws Exception {
		int index = tbContentCategoryMapper.insertSelective(category);
		if(index>0) {
			return 1;
		}else {
			throw new Exception("新增内容分类数据失败");
		}
	}

	@Override
	public int updContentCat(TbContentCategory category) throws Exception {
		int index = tbContentCategoryMapper.updateByPrimaryKeySelective(category);
		if(index>0) {
			return 1;
		}else {
			throw new Exception("更新内容分类数据失败");
		} 
	}
	
	@Override
	public TbContentCategory selById(long id) {
		return tbContentCategoryMapper.selectByPrimaryKey(id);
	}

	
}
