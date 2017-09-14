package com.xwl.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.xwl.domain.Category;
import com.xwl.domain.Order;
import com.xwl.domain.Product;
import com.xwl.utils.DataSourceUtils;

public class AdminDao {
	
	private QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());

	public List<Category> finAllCategory() throws SQLException {
		String sql = "select * from category";
		List<Category> list = runner.query(sql, new BeanListHandler<>(Category.class));
		return list;
	}

	public void addProduct(Product product) throws SQLException {
		String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
		runner.update(sql, product.getPid(),product.getPname(),product.getMarket_price(),
							product.getShop_price(),product.getPimage(),product.getPdate(),product.getIs_hot(),
							product.getPdesc(),product.getPflag(),product.getCategory().getCid());
	}

	public List<Product> findAllProduct() throws SQLException {
		String sql = "select * from product";
		List<Product> list = runner.query(sql, new BeanListHandler<>(Product.class));
		return list;
	}

	public Product findProductByPid(String pid) throws SQLException {
		String sql = "select * from product where pid = ?";
		Product product = runner.query(sql, new BeanHandler<>(Product.class), pid);
		return product;
	}

	public void updateProduct(Product product) throws SQLException {
		String sql = "update product set pname=?,market_price=?,shop_price=?,pimage=?,pdate=?,is_hot=?,pdesc=?,pflag=?,cid=? where pid = ?";
		runner.update(sql,product.getPname(),product.getMarket_price(),
				product.getShop_price(),product.getPimage(),product.getPdate(),product.getIs_hot(),product.getPdesc(),
				product.getPflag(),product.getCategory().getCid(),product.getPid());
	}

	public void deleteProductByPid(String pid) throws SQLException {
		String sql = "delete from product where pid = ?";
		runner.update(sql, pid);
	}

	public List<Order> findAllOrderList() throws SQLException {
		String sql = "select * from orders";
		List<Order> list = runner.query(sql, new BeanListHandler<>(Order.class));
		return list;
	}

	public Category getCategoryByCid(String cid) throws SQLException {
		String sql = "select * from category where cid = ?";
		Category category = runner.query(sql, new BeanHandler<>(Category.class), cid);
		return category;
	}

	public void updateCategory(String cid, String cname) throws SQLException {
		String sql = "update category set cname = ? where cid = ?";
		runner.update(sql, cname,cid);
	}

	public void deleteCategoryByCid(String cid) throws SQLException {
		String sql = "delete from category where cid = ?";
		runner.update(sql, cid);
	}

	public void addCategory(Category category) throws SQLException {
		String sql = "insert into category values(?,?)";
		runner.update(sql, category.getCid(),category.getCname());
	}

	public List<Map<String, Object>> findOrderInfoByOid(String oid) throws SQLException {
		String sql = "select p.pimage,p.pname,p.shop_price,o.count,o.subtotal from product p,orderitem o "
				+ "where o.pid = p.pid and o.oid = ?";
		return runner.query(sql, new MapListHandler(), oid);
	}

}
