package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.mapper.TbContentCategoryMapper;
import com.ego.pojo.TbContentCategory;
import com.ego.pojo.TbContentCategoryExample;

public class TbContentCategoryDubboServiceImpl implements TbContentCategoryDubboService{
	@Resource
	private TbContentCategoryMapper tbContentCategoryMapper;
	
	@Override
	public List<TbContentCategory> getCategoryList(long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		//因为逻辑删除的数据，因此只能查状态为正常的数据
		example.createCriteria().andParentIdEqualTo(parentId).andStatusEqualTo(1);
		return tbContentCategoryMapper.selectByExample(example);
	}

	@Override
	public int insContentCate(TbContentCategory category) throws Exception{
		int index = 0;
		try {
			index = tbContentCategoryMapper.insertSelective(category);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(index==1) {
			return 1;
		}else {
			throw new Exception("内容分类新增失败，数据还原!");
		}
	}

	@Override
	public int updCategory(TbContentCategory category)throws Exception {
		//更新值不为空的数据，如果修改过程中，只需要修改状态时，采用此方法，只需修改当前对象中值不为空的数据
		int index = 0;
		//这样写由于变量接收了，所以当变量值不符合条件时，就能知道是否了异常，此处try--catch可以防止报其他的异常
		try {
			index = tbContentCategoryMapper.updateByPrimaryKeySelective(category);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(index==1) {
			return 1;
		}else {
			throw new Exception("操作失败，数据还原");
		}
	}

	@Override
	public TbContentCategory selById(long id) {
		return tbContentCategoryMapper.selectByPrimaryKey(id);
	}
	

}
