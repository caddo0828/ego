package com.ssm.dubbo.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssm.commons.pojo.EasyUIDataGrid;
import com.ssm.dubbo.service.TbItemParamDubboService;
import com.ssm.mapper.TbItemCatMapper;
import com.ssm.mapper.TbItemParamMapper;
import com.ssm.pojo.TbItemParam;
import com.ssm.pojo.TbItemParamChild;
import com.ssm.pojo.TbItemParamExample;

public class TbItemParamDubboServiceImpl implements TbItemParamDubboService{
	@Resource
	private TbItemParamMapper tbItemParamMapper;
	@Resource
	private TbItemCatMapper tbItemCatMapper;
	
	@Override
	public TbItemParam selByCatId(long catId) {
		TbItemParamExample example = new TbItemParamExample();
		example.createCriteria().andItemCatIdEqualTo(catId);
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);
		if(list!=null&&list.size()>0) {
			//一个类目只能有一个类目模板
			return list.get(0);
		}
		return null;
	}

	@Override
	public EasyUIDataGrid selParamList(int page, int rows) {
		//设置分页条件
		PageHelper.startPage(page, rows);
		//查询出全部数据所对应的SQL语句
		TbItemParamExample example = new TbItemParamExample();
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);
		
		//封装成PageInfo对象
		PageInfo<TbItemParam> pi = new PageInfo<>(list);
		
		List<TbItemParamChild> childList = new ArrayList<>();
		for (TbItemParam tbItemParam : pi.getList()) {
			TbItemParamChild child = new TbItemParamChild();
			child.setId(tbItemParam.getId());
			child.setItemCatId(tbItemParam.getItemCatId());
			child.setItemCatName((tbItemCatMapper.selectByPrimaryKey(tbItemParam.getItemCatId())).getName() );
			child.setCreated(tbItemParam.getCreated());
			child.setUpdated(tbItemParam.getUpdated());
			child.setParamData(tbItemParam.getParamData());
			childList.add(child);
		}
		
		EasyUIDataGrid dataGrid = new EasyUIDataGrid();
		dataGrid.setTotal(pi.getTotal());
		dataGrid.setRows(childList);
	
		return dataGrid;
	}

	@Override
	public int delParam(long id) throws Exception {
		int index = tbItemParamMapper.deleteByPrimaryKey(id);
		if(index==1) {
			return 1;
		}else {
			throw new Exception("删除类目模板失败，数据回滚");
		}
	}

	@Override
	public int insParam(TbItemParam itemParam) throws Exception {
		int index = tbItemParamMapper.insertSelective(itemParam);
		if(index>0) {
			return 1;
		}else {
			throw new Exception("新增规格模板失败");
		}
	}
	
	

}
