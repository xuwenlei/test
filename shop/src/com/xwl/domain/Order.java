package com.xwl.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

	private String oid;// 订单号
	private Date orderTime;
	private double total;
	private int state;// 支付状态，1代表已付款，0代表未付款

	// 收货人信息
	private String address;
	private String name;
	private String telephone;

	private User user;// 该订单属于哪个用户

	// 该订单中有多少订单项
	private List<OrderItem> orderItems = new ArrayList<>();

	
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
