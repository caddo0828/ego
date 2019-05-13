package com.ego.common.pojo;

import com.ego.pojo.TbItem;

public class TbItemChild extends TbItem{
	//前台search中的jsp中，image属性是一个数组
	private String[] images;
	/**
	 * 用于判断库存是否充足
	 * 包装类走的get/set方法，普通数据类型get方式走的isEnough方法
	 */
	private Boolean enough;

	public String[] getImages() {
		return images;
	}
	public void setImages(String[] images) {
		this.images = images;
	}
	public Boolean getEnough() {
		return enough;
	}
	public void setEnough(Boolean enough) {
		this.enough = enough;
	}		
}
