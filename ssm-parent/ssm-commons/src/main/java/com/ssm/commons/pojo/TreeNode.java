package com.ssm.commons.pojo;

import java.io.Serializable;

public class TreeNode implements Serializable{
	//节点id
	private long id;
	//当前节点的名称
	private String text;
	//当前节点的状态，是父节点则closed, 不是父节点则open
	private String state;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
