package com.ssm.pojo;

import java.io.Serializable;

public class TbItemParamChild extends TbItemParam implements Serializable{
	private String itemCatName;

	public String getItemCatName() {
		return itemCatName;
	}
	public void setItemCatName(String itemCatName) {
		this.itemCatName = itemCatName;
	}
}
