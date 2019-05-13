package com.ssm.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssm.commons.pojo.EasyUIDataGrid;
import com.ssm.dubbo.service.TbItemDubboService;
import com.ssm.mapper.TbItemDescMapper;
import com.ssm.mapper.TbItemMapper;
import com.ssm.mapper.TbItemParamItemMapper;
import com.ssm.pojo.TbItem;
import com.ssm.pojo.TbItemDesc;
import com.ssm.pojo.TbItemExample;
import com.ssm.pojo.TbItemParamItem;

public class TbItemDubboServiceImpl implements TbItemDubboService{
	@Resource
	private TbItemMapper tbItemMapper;
	@Resource
	private TbItemDescMapper tbItemDescMapper;
	@Resource
	private TbItemParamItemMapper tbItemParamItemMapper;
	
	
	@Override
	public EasyUIDataGrid selItemList(int page, int rows) {
		//先设置分页条件,从第几页来使，每页显示多少条数据
		PageHelper.startPage(page, rows);
		//查询商品信息
		List<TbItem> list = tbItemMapper.selectByExample(new TbItemExample());
		//将查询后的sql语句和分页设置SQL语句拼接，反倒PageInfo对象中
		PageInfo<TbItem> pi = new PageInfo<>(list);
		EasyUIDataGrid dataGrid = new EasyUIDataGrid();
		dataGrid.setTotal(pi.getTotal());
		dataGrid.setRows(pi.getList());
		return dataGrid;
	}

	
	@Override
	public int updItems(TbItem item) throws Exception {
		int index = tbItemMapper.updateByPrimaryKeySelective(item);
		if(index>0){
			return 1;
		}else {
			throw new Exception();
		}
	}


	@Override
	public int updItemDescParam(TbItem Item, TbItemDesc itemDesc, TbItemParamItem paramItem) throws Exception {
		int index = tbItemMapper.updateByPrimaryKeySelective(Item);
		
		//先判断商品描述表中是否存在与商品id对应的描述对象
		TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(itemDesc.getItemId());
		if(tbItemDesc!=null) {
			//更新
			index += tbItemDescMapper.updateByPrimaryKeyWithBLOBs(itemDesc);
		}else {
			//新建
			index += tbItemDescMapper.insertSelective(itemDesc);
		}
		
		//先判断商品具体规格对象是否存在，存在就更新，不存在则新增
		TbItemParamItem itemParamItem = tbItemParamItemMapper.selectByPrimaryKey(paramItem.getId());
		if(itemParamItem!=null) {
			//更新
			index += tbItemParamItemMapper.updateByPrimaryKeyWithBLOBs(paramItem);			
		}else {
			index += tbItemParamItemMapper.insertSelective(paramItem);
		}
		
		if(index==3) {
			return 1;
		}else {
			throw new Exception("修改商品失败，数据回滚");
		}
		
	}


	@Override
	public int insItemDescParam(TbItem item, TbItemDesc itemDesc, TbItemParamItem paramItem) throws Exception {
		int index = tbItemMapper.insertSelective(item);
		index += tbItemDescMapper.insertSelective(itemDesc);
		index += tbItemParamItemMapper.insertSelective(paramItem);
		if(index==3) {
			return 1;
		}else {
			throw new Exception("新增商品失败，数据回滚");
		}
	}
	

}
