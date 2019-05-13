package com.ego.manager.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.common.pojo.Result;
import com.ego.common.pojo.TreeNode;
import com.ego.common.utils.IDUtils;
import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.manager.service.TbContentCategoryService;
import com.ego.pojo.TbContentCategory;


@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService{
	@Reference
	private  TbContentCategoryDubboService tbContentCategoryDubboServiceImpl;

	@Override
	public List<TreeNode> selCategroyList(long parentId) {
		List<TreeNode> nodesList = new ArrayList<>();
		List<TbContentCategory> categoryList = tbContentCategoryDubboServiceImpl.getCategoryList(parentId);
		for (TbContentCategory category : categoryList) {
			TreeNode node = new TreeNode();
			node.setId(category.getId());
			node.setText(category.getName());
			node.setState(category.getIsParent()?"closed":"open");
			nodesList.add(node);
		}
		return nodesList;
	}

	
	@Override
	public Result insCategroy(TbContentCategory category){
		Result result = new Result();
		//首先判断查询该内容类目的父类目所包含的子类目中是否有和当前新增的类目名称一致的，如果有，则新增失败
		List<TbContentCategory> cateChilds = tbContentCategoryDubboServiceImpl.getCategoryList(category.getParentId());
		for (TbContentCategory child : cateChilds) {
			if(child.getName().equals(category.getName())) {
				//代表类目名称包含一样的，新增失败
				result.setData("新增类目名称不能相同");
				return result;
			}
		}
		//新增分类信息的时候，要对父分类状态进行修改,并且要修改父类被修改的时间
		long id = IDUtils.getItemId();
		Date date = new Date();
		category.setId(id);
		category.setSortOrder(1);
		//新增的数据，不是一个父节点
		category.setIsParent(false);
		category.setCreated(date);
		category.setUpdated(date);
		
		//同时对父节点信息进行修改操作
		TbContentCategory parentCate = new TbContentCategory();
		parentCate.setId(category.getParentId());
		parentCate.setIsParent(true);
		parentCate.setUpdated(date);
		int index = 0;
		try {
			index =  tbContentCategoryDubboServiceImpl.insContentCate(category);
			index += tbContentCategoryDubboServiceImpl.updCategory(parentCate);
			if(index==2) {
				result.setStatus(200);
				//要传递新增分类的id响应给前台，因此传递的data属性值应该是key-value形式
				//要么是自定义对象，要么是集合，因此考虑到需要传递的数据值比较少，采用传递集合的方式比较快捷
				Map<String, Long> map = new HashMap<>();
				map.put("id", id);
				result.setData(map);
			}else {
				result.setData("数据新增失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	
	@Override
	public Result updCategroy(TbContentCategory category) {
		Result result = new Result();
		//判断当前节点的父节点的所有子节点中是否存在同名的内容分类
		//获取当前节点的内容分类节点
		TbContentCategory currCategory = tbContentCategoryDubboServiceImpl.selById(category.getId());
		//获取当前节点下对应的父节点下的所有子节点
		List<TbContentCategory> childNodes = tbContentCategoryDubboServiceImpl.getCategoryList(currCategory.getParentId());
		for (TbContentCategory childs : childNodes) {
			if(childs.getName().equals(category.getName())) {
				result.setData("不能出现相同的节点名称");
				return result;
			}
		}
		//自动把修改的时间进行设置
		Date date = new Date();
		category.setUpdated(date);
		int index = 0;
		try {
			index = tbContentCategoryDubboServiceImpl.updCategory(category);
			if(index ==1 ) {
				result.setStatus(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	//先判断删除的节点是否是一个父节点
	//1、父节点，则修改状态的同时对所有的子节点的状态进行修改
	//2、不是父节点时，只修改当前节点的状态，并且判断该节点的父节点是否还有子节点，还要子节点的话，就不修改状态，否则修改状态
	@Override
	public Result delContentCate(long id)  {
		Result result = new Result();
		//判断节点是否为父节点
		TbContentCategory category = new TbContentCategory();
		Date date = new Date();
		category.setId(id);
		//删除类目
		category.setStatus(2);
		category.setUpdated(date);
		//先进行分类删除
		int index = 0;
		try {
			index = tbContentCategoryDubboServiceImpl.updCategory(category);
			if(index==1) {
				//删除类目成功
				//根据当前类目id查询当前类目对象
				TbContentCategory currCategroy = tbContentCategoryDubboServiceImpl.selById(id);
				//获取当前节点的父节点下的所有子节点
				List<TbContentCategory> categoryList = tbContentCategoryDubboServiceImpl.getCategoryList(currCategroy.getParentId());
				if(categoryList==null||categoryList.size()==0) {
					//表示当前节点的父节点下已经不存在子节点了
					//修改父节点状态
					TbContentCategory parent = new TbContentCategory();
					parent.setId(currCategroy.getParentId());
					parent.setIsParent(false);
					parent.setUpdated(date);
					int flag = 0;
					try {
						flag = tbContentCategoryDubboServiceImpl.updCategory(parent);
						if(flag==1) {
							result.setStatus(200);
						}else {
							result.setData("修改父节点状态失败，数据回滚");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else {
					result.setStatus(200);
				}
			}else {
				result.setData("删除内容分类失败，数据回滚");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	
	
	
}
