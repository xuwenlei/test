package com.ssh.shop.service;

import java.util.List;

import com.ssh.shop.dao.ProductDao;
import com.ssh.shop.entities.Category;
import com.ssh.shop.entities.OrderItem;
import com.ssh.shop.entities.Orders;
import com.ssh.shop.entities.PageBean;
import com.ssh.shop.entities.Product;

public class ProductService {

	private ProductDao dao = new ProductDao();
	
	public void setDao(ProductDao dao) {
		this.dao = dao;
	}

	public List<Product> findHotProductList() {
		List<Product> hotList = dao.findHotProductList();
		return hotList;
	}

	public List<Product> findNewProductList() {
		List<Product> newList = dao.findNewProductList();
		return newList;
	}

	public List<Category> findAllCategory() {
		List<Category> categories = dao.findAllCategory();
		return categories;
	}

	public PageBean<Product> findProductListByCid(String cid, int currentPage,
			int currentCount) {
		// 封装一个pagebean
		PageBean<Product> pageBean = new PageBean<>();

		pageBean.setCurrentPage(currentPage);
		pageBean.setCurrentCount(currentCount);
		int totalCount = 0;
		totalCount = dao.getCount(cid);
		pageBean.setTotalCount(totalCount);
		int totalPage = (int) Math.ceil(1.0 * totalCount / currentCount);
		pageBean.setTotalPage(totalPage);

		int index = (currentPage - 1) * currentCount;
		List<Product> list = null;
		list = dao.findProductListByCid(cid, index, currentCount);
		pageBean.setList(list);

		return pageBean;
	}

	public Product findProductByPid(String pid) {
		Product product = null;
		product = dao.findProductByPid(pid);
		return product;
	}

	// 提交订单，将数据存储到数据库
	public void submitOrder(Orders order) {
		// 调用存储order表数据方法
		dao.addOrders(order);
		// 调用存储orderitem表数据方法
		// dao.addOrderItem(order);
	}

	public void updateOrderAddr(Orders order) {
		dao.updateOrderAddr(order);
	}

	public void updateOrderState(String r6_Order) {
		// dao.updateOrderState(r6_Order);
	}

	public List<Orders> finAllOrders(String uid) {
		List<Orders> OrdreList = null;
		OrdreList = dao.finAllOrders(uid);
		return OrdreList;
	}

	public List<OrderItem> findAllOrderItems(String oid) {
		List<OrderItem> list = dao.findAllOrderItems(oid);
		return list;
	}

	public Category findCategoryByCid(String cid) {
		Category category = dao.findCategoryByCid(cid);
		return category;
	}

}
