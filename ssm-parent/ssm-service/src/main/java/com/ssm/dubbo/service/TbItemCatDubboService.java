package com.ssm.dubbo.service;

import java.util.List;
import com.ssm.pojo.TbItemCat;

public interface TbItemCatDubboService {
	/**
	 * 根据父类目id查询出当前类目及子类目信息，（父类目id=类目id），循环遍历查询自己的过程
	 * @param parentId
	 * @return
	 */
	List<TbItemCat> selCatList(long parentId);
	
		
}
