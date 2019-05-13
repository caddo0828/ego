package com.ego.dubbo.service.impl;

import java.util.List;
import javax.annotation.Resource;
import com.ego.common.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.mapper.TbItemDescMapper;
import com.ego.mapper.TbItemMapper;
import com.ego.mapper.TbItemParamItemMapper;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemExample;
import com.ego.pojo.TbItemParamItem;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 此处不直接交给spring去管理，而是交给dubbo管理，不加@Service注解
 * 以免出现数据库声明事务起冲突
 * @author 老腰
 */
public class TbItemDubboServiceImpl implements TbItemDubboService{
    @Resource
	private TbItemMapper tbItemMapper;
    @Resource
    private TbItemDescMapper tbItemDescMapper;
    @Resource
	private TbItemParamItemMapper tbItemParamItemMapper;
    
	@Override
	public EasyUIDataGrid getTbItemList(int page, int rows) {
		//分页处理, 自动化在sql语句中添加limit查询条件
		//设置分页条件，根据当前页，以及当前页要显示的数据进行分页显示
		PageHelper.startPage(page, rows);
		//没有查询条件，直接查询出总的数据
		List<TbItem> list = tbItemMapper.selectByExample(new TbItemExample());
		
		//page插件中所使用的功能，对Tbitem进行分页
		//pi中会包含所有与分页有关的信息
		PageInfo<TbItem> pi = new PageInfo<>(list);
				
		//取出总的记录数,放入到实体类中
		EasyUIDataGrid dataGrid = new EasyUIDataGrid();
		dataGrid.setRows(pi.getList());
		dataGrid.setTotal(pi.getTotal());	
		return dataGrid;
	}

	@Override
	public int updTbItem(TbItem item) throws Exception{
		return tbItemMapper.updateByPrimaryKeySelective(item);
	}

	@Override
	public int insTbItem(TbItem item) {
		return tbItemMapper.insertSelective(item);
	}

	/**
	 * 同时进行商品的新增的同时对描述表进行新增
	 * 此时商品id需要自己设置，并且和商品描述表中的id一致
	 * @throws Exception 
	 */
	@Override
	public int insTbItemAndDesc(TbItem item, TbItemDesc ItemDesc,TbItemParamItem itemParamItem) throws Exception {
		int index = 0;
		try {
			index = tbItemMapper.insertSelective(item);
			index += tbItemDescMapper.insertSelective(ItemDesc);
			index += tbItemParamItemMapper.insertSelective(itemParamItem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(index==3) {
			//代表两个表的数据都新增成功
			return 1;
		}else {
			throw new Exception("商品新增失败，数据回滚！");
		}
		
	}

	@Override
	public List<TbItem> selAllByStatu(byte status) {
		TbItemExample example = new TbItemExample();
		example.createCriteria().andStatusEqualTo(status);
		return tbItemMapper.selectByExample(example);
	}

	@Override
	public TbItem selById(long id) {
		TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
		return tbItemMapper.selectByPrimaryKey(id);
	}

	
}
