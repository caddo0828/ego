package com.ego.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * easyuidataGrid中ajax请求需要返回的数据
 * @author 老腰
 *
 */
public class EasyUIDataGrid implements Serializable{
	//查询结果的总记录数
	private long total;
	//？ 代表任意类型，返回的数据集合
	private List<?> rows;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	
}
