package com.ego.dubbo.service.impl;

import java.util.List;
import javax.annotation.Resource;
import com.ego.common.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.mapper.TbItemParamMapper;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TbItemParamDubboServiceImpl implements TbItemParamDubboService{
	@Resource
	private TbItemParamMapper tbItemParamMapper;

	@Override
	public EasyUIDataGrid selItemParamList(int page, int rows) {
		//先设计分页条件
		PageHelper.startPage(page, rows);
		//查询全部的SQL语句,注意事项使用selectByExampleWithBLOBs才能查询出数据类型为text的数据
		List<TbItemParam>  paramList = tbItemParamMapper.selectByExampleWithBLOBs(new TbItemParamExample());		//将分页条件进行封装成PageInfo对象
		PageInfo<TbItemParam> pageInfo = new PageInfo<>(paramList);
		EasyUIDataGrid dataGrid = new EasyUIDataGrid();
		dataGrid.setTotal(pageInfo.getTotal());
		dataGrid.setRows(pageInfo.getList());
		return dataGrid;
	}

	@Override
	public int delItemParams(long id) throws Exception{
		return tbItemParamMapper.deleteByPrimaryKey(id);	
	}

	@Override
	public TbItemParam selByCatId(long itemCatId) {
		TbItemParamExample example = new TbItemParamExample();
		example.createCriteria().andItemCatIdEqualTo(itemCatId);
		//使用selectByExampleWithBLOBs该方法查询出对应的包含了text数据类型的数据
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);
		if(list!=null&&list.size()>0) {
			//每个类目只有一个模板，虽然查询出的是集合，但是实际上只有一条数据
			return list.get(0);
		}
		return null;
	}

	
	@Override
	public int insItemParam(TbItemParam itemParam) {
		return tbItemParamMapper.insertSelective(itemParam);
	}

}
