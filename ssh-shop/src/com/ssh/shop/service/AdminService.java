package com.ssh.shop.service;

import java.util.List;

import com.ssh.shop.dao.AdminDao;
import com.ssh.shop.entities.Category;
import com.ssh.shop.entities.Orders;
import com.ssh.shop.entities.Product;

public class AdminService {

	private AdminDao dao = new AdminDao();
	
	public void setDao(AdminDao dao) {
		this.dao = dao;
	}

	public List<Category> findAllCategory() {
		List<Category> categories = dao.finAllCategory();
		return categories;
	}

	public void addProduct(Product product) {
		dao.addProduct(product);
	}

	public List<Product> findAllProduct() {
		List<Product> products = dao.findAllProduct();
		return products;
	}

	public Product findProductByPid(String pid) {
		Product product = dao.findProductByPid(pid);
		return product;
	}

	public void updateProduct(Product product) {
		dao.updateProduct(product);
	}

	public void deleteProductByPid(String pid) {
		dao.deleteProductByPid(pid);
	}

	public List<Orders> findAllOrderList() {
		List<Orders> orders = dao.findAllOrderList();
		return orders;
	}

	public Category getCategoryByCid(String cid) {
		Category category = dao.getCategoryByCid(cid);
		return category;
	}

	public void updateCategory(String cid, String cname) {
		// dao.updateCategory(cid, cname);
	}

	public void deleteCategoryByCid(String cid) {
		dao.deleteCategoryByCid(cid);
	}

	public void addCategory(Category category) {
		dao.addCategory(category);
	}

	public List<Orders> findOrderInfoByOid(String oid) {
		List<Orders> list = dao.findOrderInfoByOid(oid);
		return list;
	}

}
