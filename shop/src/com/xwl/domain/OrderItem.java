package com.xwl.domain;

//订单项
public class OrderItem {

	private String itemid;
	private int count;
	private double subTotal;
	private Product product;
	private Order order;// 该订单项属于哪个订单

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subtotal) {
		this.subTotal = subtotal;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
