package com.xwl.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.xwl.dao.AdminDao;
import com.xwl.domain.Category;
import com.xwl.domain.Order;
import com.xwl.domain.Product;

public class AdminService {
	
	private AdminDao dao = new AdminDao();

	public List<Category> findAllCategory() {
		List<Category> categories = null;
		try {
			categories = dao.finAllCategory();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categories;
	}

	public void addProduct(Product product) {
		try {
			dao.addProduct(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Product> findAllProduct() {
		List<Product> products = null;
		try {
			products = dao.findAllProduct();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}

	public Product findProductByPid(String pid) {
		Product product = null;
		try {
			product = dao.findProductByPid(pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
	}

	public void updateProduct(Product product) {
		try {
			dao.updateProduct(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteProductByPid(String pid) {
		try {
			dao.deleteProductByPid(pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Order> findAllOrderList() {
		List<Order> orders = null;
		try {
			orders = dao.findAllOrderList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}

	public Category getCategoryByCid(String cid) {
		Category category = null;
		try {
			category = dao.getCategoryByCid(cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return category;
	}

	public void updateCategory(String cid, String cname) {
		try {
			dao.updateCategory(cid,cname);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteCategoryByCid(String cid) {
		try {
			dao.deleteCategoryByCid(cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addCategory(Category category) {
		try {
			dao.addCategory(category);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Map<String, Object>> findOrderInfoByOid(String oid) {
		List<Map<String, Object>> mapList = null;
		try {
			mapList = dao.findOrderInfoByOid(oid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapList;
	}

}
