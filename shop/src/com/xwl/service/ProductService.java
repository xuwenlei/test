package com.xwl.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.xwl.dao.ProductDao;
import com.xwl.domain.Category;
import com.xwl.domain.Order;
import com.xwl.domain.PageBean;
import com.xwl.domain.Product;
import com.xwl.utils.DataSourceUtils;

public class ProductService {

	private ProductDao dao = new ProductDao();

	public List<Product> findHotProductList() {
		List<Product> hotList = null;
		try {
			hotList = dao.findHotProductList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		;
		return hotList;
	}

	public List<Product> findNewProductList() {
		List<Product> newList = null;
		try {
			newList = dao.findNewProductList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		;
		return newList;
	}

	public List<Category> findAllCategory() {
		List<Category> categories = null;
		try {
			categories = dao.findAllCategory();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categories;
	}

	public PageBean<Product> findProductListByCid(String cid, int currentPage, int currentCount) {
		// 封装一个pagebean
		PageBean<Product> pageBean = new PageBean<>();

		pageBean.setCurrentPage(currentPage);
		pageBean.setCurrentCount(currentCount);
		int totalCount = 0;
		try {
			totalCount = dao.getCount(cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pageBean.setTotalCount(totalCount);
		int totalPage = (int) Math.ceil(1.0 * totalCount / currentCount);
		pageBean.setTotalPage(totalPage);

		int index = (currentPage - 1) * currentCount;
		List<Product> list = null;
		try {
			list = dao.findProductListByCid(cid, index, currentCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		;
		pageBean.setList(list);

		return pageBean;
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

	// 提交订单，将数据存储到数据库
	public void submitOrder(Order order) {
		try {
			// 开启事务
			DataSourceUtils.startTransaction();
			// 调用存储order表数据方法
			dao.addOrders(order);
			// 调用存储orderitem表数据方法
			dao.addOrderItem(order);
		} catch (SQLException e) {
			try {
				DataSourceUtils.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				DataSourceUtils.commitAndRelease();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateOrderAddr(Order order) {
		try {
			dao.updateOrderAddr(order);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateOrderState(String r6_Order) {
		try {
			dao.updateOrderState(r6_Order);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Order> finAllOrders(String uid) {
		List<Order> OrdreList = null;
		try {
			OrdreList = dao.finAllOrders(uid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return OrdreList;
	}

	public List<Map<String, Object>> findAllOrderItems(String oid) {
		List<Map<String, Object>> mapList = null;
		try {
			mapList = dao.findAllOrderItems(oid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapList;
	}

	public Category findCategoryByCid(String cid) {
		Category category = null;
		try {
			category = dao.findCategoryByCid(cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return category;
	}

}
