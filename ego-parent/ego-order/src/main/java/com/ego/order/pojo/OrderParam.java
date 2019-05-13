package com.ego.order.pojo;

import java.util.List;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

/**
 * 创建订单，复杂数据类型传值，转换成对象
 * @author 老腰
 */
public class OrderParam {
	private int paymentType;
	private String payment;
	private List<TbOrderItem> orderItems;
	private TbOrderShipping orderShipping;
	public int getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(int paymentType) {
		this.paymentType = paymentType;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public List<TbOrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<TbOrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}
	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}
}
