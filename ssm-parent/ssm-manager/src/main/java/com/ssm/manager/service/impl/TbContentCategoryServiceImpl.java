package com.ssm.manager.service.impl;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.ssm.commons.pojo.Result;
import com.ssm.commons.pojo.TreeNode;
import com.ssm.commons.utils.IDUtils;
import com.ssm.dubbo.service.TbContentCategoryDubboService;
import com.ssm.manager.service.TbContentCategoryService;
import com.ssm.pojo.TbContentCategory;

@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService{
	@Reference
	private TbContentCategoryDubboService tbContentCategoryDubboServiceImpl;
	
	@Override
	public List<TreeNode> selCatList(long parentId) {
		List<TreeNode> nodeList = new ArrayList<>();
		List<TbContentCategory> list = tbContentCategoryDubboServiceImpl.selCatList(parentId);
		if(list!=null&&list.size()>0) {
			for (TbContentCategory tbContentCategory : list) {
				TreeNode node = new TreeNode();
				node.setId(tbContentCategory.getId());
				node.setText(tbContentCategory.getName());
				node.setState(tbContentCategory.getIsParent()? "closed":"open");
				nodeList.add(node);
			}
		}
		return nodeList;
	}

	@Override
	public Result insConteCate(TbContentCategory category) {
		Result result = new Result();
		// prarenid  name
		long id = IDUtils.getItemId();
		Date date = new Date();
		category.setId(id);
		category.setSortOrder(1);
		category.setIsParent(false); //新增的内容分类不是一个父类目
		category.setCreated(date);
		category.setUpdated(date);
		
		//通过父类目id查询出是否存在内容分类集合对象，存在，代表当前节点是一个父节点
		//是父节点，则判断子节点中是否存在和当前新增的内容名称一致的，一致则新增失败，否则成功
		//不是父节点，直接新增并且修改该被选中节点为“父节点”
		List<TbContentCategory> list = tbContentCategoryDubboServiceImpl.selCatList(category.getParentId());
		if(list!=null&&list.size()>0) {
			//代表被选中节点为“父节点” 
			for (TbContentCategory tbContentCategory : list) {
				if(tbContentCategory.getName().equals(category.getName())) {
					result.setData("新增内容分类名称不能存在相同的，请重新操作");
					return result;
				}
			}
		}
		
		//该类目是父类目且该类目下不存在子类目名称中有一致的，2、该类目不是一个父类目
		TbContentCategory parentCate = new TbContentCategory();
		parentCate.setId(category.getParentId());
		//不管前面是不是一个父节点，恒修改父节点状态
		parentCate.setIsParent(true);
		parentCate.setUpdated(date);
		
		//新增子节点和修改父节点同时操作
		int index = 0;
		try {
			index += tbContentCategoryDubboServiceImpl.insContentCat(category);
			index += tbContentCategoryDubboServiceImpl.updContentCat(parentCate);
			if(index==2) {
				result.setStatus(200);
				//前台需要从传递返回的结果中获取data属性中的id,因此传递的data属性应该是一个新增成功的对象，或者一个键名为“id”的map集合
				result.setData(category);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setData("节点新增给失败，数据回滚");
		}
		return result;
	}

	@Override
	public Result updConteCate(TbContentCategory category) {
		Date date = new Date();
		//设置重命名的类目修改时间
		category.setUpdated(date);
		
		Result result = new Result();
		try {
			int index = tbContentCategoryDubboServiceImpl.updContentCat(category);
			if(index>0) {
				result.setStatus(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setData("内容类目重命名失败，请重新操作");
		}
		return result;
	}

	//删除操作为物理删除
	@Override
	public Result delConteCate(long id) {
		Result result = new Result();
		Date date = new Date();
		TbContentCategory category = new TbContentCategory();
		category.setId(id);
		category.setUpdated(date);
		category.setStatus(2);
	
		//先删除当前类目
		int index = 0 ;
		try {
			index = tbContentCategoryDubboServiceImpl.updContentCat(category);
			if(index==1) {
				//删除成功
				//通过id获取当前的类目对象，通过类目查询父类目id,（前提，必须在当前节点删除成功后操作）在判断父类目id下是否还存在其他子节点
				TbContentCategory currCate = tbContentCategoryDubboServiceImpl.selById(id);
				//获取父类目下的所有子类目集合
				List<TbContentCategory> catList = tbContentCategoryDubboServiceImpl.selCatList(currCate.getParentId());
				if(catList==null||catList.size()==0) {
					//当前父类目下已经没有子类目
					TbContentCategory parentCate = new TbContentCategory();
					parentCate.setId(currCate.getParentId());
					parentCate.setUpdated(date);
					parentCate.setIsParent(false);
					
					//修改父节点信息
					 try {
						int num = tbContentCategoryDubboServiceImpl.updContentCat(parentCate);
						if(num>0) {
							result.setStatus(200);
						}
					 } catch (Exception e) {
						e.printStackTrace();
						result.setData("类目删除失败，请重新操作");
					}	
				}else {
					result.setStatus(200);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setData("类目删除失败，请重新操作");
		}
		return result;
	}

}
