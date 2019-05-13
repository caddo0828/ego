package com.ego.manager.service;

import com.ego.common.pojo.EasyUIDataGrid;
import com.ego.common.pojo.Result;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;

public interface TbItemService {
	EasyUIDataGrid getTbItemList(int page,int rows);
	
	Result updTbItem(String ids,byte status);
	
	int insTbItemAndDesc(TbItem item,String desc,String itemParams)throws Exception;
}
