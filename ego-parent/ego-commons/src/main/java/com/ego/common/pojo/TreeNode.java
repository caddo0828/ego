package com.ego.common.pojo;

import java.io.Serializable;

/**
 * 创建easyui的 treenode树型控件节点
 * @author 老腰
 *
 */
public class TreeNode implements Serializable{
	//当前节点的id
	private long id;
	//节点显示的名称 ,就是商品的类别名称
	private String text;
	//节点的状态，如果是closed就是一个文件夹形式，
    //当打开时还会 做一次请求。如果是open就显示为叶子节点。
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
